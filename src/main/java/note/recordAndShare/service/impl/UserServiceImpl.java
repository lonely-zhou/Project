package note.recordAndShare.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upyun.Result;
import com.upyun.UpException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Role;
import note.recordAndShare.entity.Settings;
import note.recordAndShare.entity.User;
import note.recordAndShare.entity.dto.UserDto;
import note.recordAndShare.mapper.RoleMapper;
import note.recordAndShare.mapper.SettingsMapper;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.*;
import note.utils.sms.SMSService;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static note.utils.UpYunUtil.init;
import static note.utils.UpYunUtil.testSync;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    private final SettingsMapper settingsMapper;
    private final RoleMapper roleMapper;
    private final SMSService smsService;
    private final EmailUtil emailUtil;


    /**
     * 查找用户是否存在
     *
     * @param username 用户名
     * @return 0 or 1
     */
    @Override
    public int isUser(String username) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("username", username)).intValue();
    }

    /**
     * 新增用户
     *
     * @param user 包含账户名与密码
     * @return 受影响条数
     */
    @Override
    public int insUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(), user.getUsername()));
        user.setLastTime(new TimeUtil().getFormatDateForFive());
        int count = userMapper.insert(user);
        if (count > 0) {
            Settings settings = new Settings();
            settings.setUserId(user.getId());
            settingsMapper.insert(settings);
            redisUtil.hset(ConstantUtil.USER_NAME, user.getUsername(), user.getUsername());
            return 0;
        }
        return 1;
    }

    /**
     * 更新密码
     *
     * @param user username password
     * @return ""
     */
    @Override
    public NoteResultUtil updPassword(User user) {
        user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(), user.getUsername()));
        int count = userMapper.update(user, new UpdateWrapper<User>().eq("username", user.getUsername()));
        if (count > 0) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("修改密码失败");
    }

    /**
     * 登录
     *
     * @param user      用户名与密码
     * @param autoLogin 自动登录
     * @param isAdmin   是否为管理员
     * @return ok
     */
    @Override
    public NoteResultUtil login(User user, boolean autoLogin, boolean isAdmin) {
        try {
            int role = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername())).getRoleId();
            if (isAdmin) {
                if (role != ConstantUtil.ADMIN_ROLE && role != ConstantUtil.SU_ADMIN_ROLE) {
                    return NoteResultUtil.error("无管理员权限");
                }
            }

            User userForMySql = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            String password = userForMySql.getPassword();
            String userId = userForMySql.getId();
            if (password.equals(SaSecureUtil.md5BySalt(user.getPassword(), user.getUsername()))) {
                StpUtil.login(user.getUsername(), new SaLoginModel().setExtra("user_id", userId).setIsLastingCookie(autoLogin));
                user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(), user.getUsername()));
                user.setLastTime(new TimeUtil().getFormatDateForFive());
                userMapper.update(user, new QueryWrapper<User>().eq("username", user.getUsername()));
                return NoteResultUtil.success();
            } else {
                return NoteResultUtil.error("密码错误");
            }
        } catch (Exception e) {
            return NoteResultUtil.error("用户名不存在");
        }
    }

    /**
     * 是否登录
     *
     * @return 是否
     */
    @Override
    public NoteResultUtil isLogin() {
        String username = StpUtil.getLoginId().toString();
        int role = userMapper.selectOne(new QueryWrapper<User>().eq("username", username)).getRoleId();
        String rname = roleMapper.selectOne(new QueryWrapper<Role>().eq("rid", role)).getRname();
        UserDto userDto = new UserDto();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        BeanUtil.copyProperties(user, userDto);
        return NoteResultUtil.success(MapUtil.builder().put("user", userDto).put("role", rname).put("isLogin", StpUtil.isLogin()).map());
    }

    /**
     * 注册
     *
     * @param user 用户名与密码
     * @return ok
     */
    @Override
    public NoteResultUtil signup(User user) {
        if (redisUtil.hsize(ConstantUtil.USER_NAME) != 0) {
            if (!redisUtil.hasHkey(ConstantUtil.USER_NAME, user.getUsername())) {
                insUser(user);
                return NoteResultUtil.success();
            } else {
                return NoteResultUtil.error("用户已存在");
            }
        } else {
            if (isUser(user.getUsername()) < 1) {
                insUser(user);
                return NoteResultUtil.success();
            } else {
                return NoteResultUtil.error("注册失败");
            }
        }
    }

    /**
     * 密码重置-查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public NoteResultUtil selUser(String username) {
        if (redisUtil.hasHkey(ConstantUtil.USER_NAME, username)) {
            return NoteResultUtil.success(userMapper.selectOne(new QueryWrapper<User>().eq("username", username)));
        } else {
            if (isUser(username) == 1) {
                return NoteResultUtil.success(userMapper.selectOne(new QueryWrapper<User>().eq("username", username)));
            }
        }
        return NoteResultUtil.error("用户不存在");
    }

    /**
     * 密码重置 - 发送短信验证码
     *
     * @param username 用户名
     * @return ""
     */
    @Override
    public NoteResultUtil sendSmsCode(String username) {
        String result = smsService.sendSms(String.valueOf(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone"))), 1);
        if (ConstantUtil.OK.equals(result)) {
            return NoteResultUtil.success();
        } else {
            return NoteResultUtil.error("获取验证码失败");
        }
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return ok
     * @throws IOException        输入或输出异常
     * @throws TemplateException  加载和解析模板异常
     * @throws MessagingException 邮件发送异常
     */
    @Override
    public NoteResultUtil sendEmail(String email) throws IOException, TemplateException, MessagingException {
        // 验证码随机数
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ConstantUtil.EMAIL_CODE_DIGITS; i++) {
            result.append(Math.round(Math.random() * 9));
        }
        String code = result.toString();
        /*验证码存入缓存*/
        redisUtil.set(ConstantUtil.USER_EMAIL + email, code);
        /*设置过期时间 300s*/
        redisUtil.expire(ConstantUtil.USER_EMAIL + email, 300);

        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "templates");
        Template template = configuration.getTemplate("emailCode.ftl");
        Map<String, String> map = new HashMap<>(50);
        map.put("code", code);
        String stringResult = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

        emailUtil.sendSimpleHtmlMail(email, "记享-验证邮件", stringResult);
        return NoteResultUtil.success();
    }

    /**
     * 更新邮箱
     *
     * @param user 用户名 邮箱
     * @return 用户信息
     */
    @Override
    public NoteResultUtil updUserEmail(User user) {
        if (redisUtil.hasKey(ConstantUtil.USER_EMAIL + user.getEmail())) {
            return getNoteResultUtil(user);
        }
        return NoteResultUtil.error("验证码失效");
    }

    /**
     * 密码重置 - 校验验证码 - 手机号
     *
     * @param username 用户名
     * @return 验证码
     */
    @Override
    public NoteResultUtil getSmsCode(String username) {
        String phone = String.valueOf(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone")));
        if (redisUtil.hasKey(ConstantUtil.USER_PHONE_CODE + phone)) {
            return NoteResultUtil.success(redisUtil.get(ConstantUtil.USER_PHONE_CODE + phone));
        } else {
            redisUtil.del(ConstantUtil.USER_PHONE_CODE + phone);
            return NoteResultUtil.error("验证码失效");
        }
    }

    /**
     * 密码重置 - 校验验证码 - 邮箱
     *
     * @param username 用户名
     * @return 验证码
     */
    @Override
    public NoteResultUtil getEmailCode(String username) {
        String email = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("email")).getEmail();
        if (redisUtil.hasKey(ConstantUtil.USER_EMAIL + email)) {
            return NoteResultUtil.success(redisUtil.get(ConstantUtil.USER_EMAIL + email));
        } else {
            redisUtil.del(ConstantUtil.USER_EMAIL + email);
            return NoteResultUtil.error("验证码失效");
        }
    }

    /**
     * 更新手机号码 - 发送验证码
     *
     * @param phone 手机号码
     * @return ok
     */
    @Override
    public NoteResultUtil sendSms(String phone) {
        String result = smsService.sendSms(phone, 2);
        if (ConstantUtil.OK.equals(result)) {
            return NoteResultUtil.success();
        } else {
            return NoteResultUtil.error("获取验证码失败");
        }
    }

    /**
     * 更新手机号码
     *
     * @param user 用户名 手机号
     * @return 用户信息
     */
    @Override
    public NoteResultUtil updUserPhone(User user) {
        if (redisUtil.hasKey(ConstantUtil.USER_PHONE_CODE + user.getPhone())) {
            return getNoteResultUtil(user);
        }
        return NoteResultUtil.error("验证码失效");
    }

    /**
     * 更新头像
     *
     * @param file     头像
     * @param username 用户名
     * @return ok
     */
    @Override
    public NoteResultUtil updAvatar(MultipartFile file, String username) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String imgMd5 = DigestUtil.md5Hex(file.getInputStream());
        User user = new User();
        if (redisUtil.hasHkey(ConstantUtil.USER_AVATAR, imgMd5)) {
            user.setAvatarUrl(redisUtil.hget(ConstantUtil.USER_AVATAR, imgMd5).toString());
        } else {
            String imgName = file.getOriginalFilename();
            assert imgName != null;
            String imgSuffix = imgName.substring(imgName.lastIndexOf("."));
            imgName = imgMd5 + imgSuffix;
            byte[] bytes = file.getBytes();
            Result result = testSync(bytes, imgName, username);
            JSONObject jsonObject = JSONObject.parseObject(result.getMsg());
            String imgUrl = "https://img.lonelyzhou.cn" + jsonObject.getString("url");
            redisUtil.hset(ConstantUtil.USER_AVATAR, imgMd5, imgUrl);
            user.setAvatarUrl(imgUrl);
        }
        userMapper.update(user, new QueryWrapper<User>().eq("username", username));
        return NoteResultUtil.success();
    }

    /**
     * 查询历史头像
     *
     * @return 头像URL
     * @throws UpException 文件上传异常
     * @throws IOException 输入或输出异常
     */
    @Override
    public NoteResultUtil selUserHistoricalAvatar() throws UpException, IOException {
        String path = (String) StpUtil.getLoginId();
        List<String> userAvatars = new ArrayList<>();
        path = "/note/userAvatar/" + path + "/";
        Response response = init().readDirIter(path, null);
        assert response.body() != null;
        String[] avatarResult = response.body().string().split("\n");
        path = "https://img.lonelyzhou.cn" + path;
        for (String s : avatarResult) {
            userAvatars.add(path + s.split("\t")[0]);
        }
        response.close();
        return NoteResultUtil.success(userAvatars);
    }

    /**
     * 更新用户头像 -- 历史头像
     *
     * @param avatarUrl 头像url
     * @return ok
     */
    @Override
    public NoteResultUtil updSelAvatar(String avatarUrl) {
        User user = new User();
        user.setId(StpUtil.getExtra("user_id").toString());
        user.setAvatarUrl(avatarUrl);
        if (userMapper.updateById(user) == 1) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("更新头像失败");
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public NoteResultUtil updUser(User user) {
        return getNoteResultUtil(user);
    }

    private NoteResultUtil getNoteResultUtil(@RequestBody User user) {
        userMapper.update(user, new QueryWrapper<User>().eq("username", user.getUsername()));
        User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if ("".equals(user.getNickname())) {
            user.setNickname(user1.getNickname());
        }
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user1, userDto);
        return NoteResultUtil.success(userDto);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return ok
     */
    @Override
    public NoteResultUtil delUser(String userId) {
        String username = userMapper.selectById(userId).getUsername();
        int count = userMapper.deleteById(userId);
        if (count == 1) {
            redisUtil.hdel(ConstantUtil.USER_NAME, username);
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("注销失败");
    }

}
