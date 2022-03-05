package note.recordAndShare.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upyun.Result;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import note.recordAndShare.entity.User;
import note.recordAndShare.entity.dto.UserDto;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import note.utils.*;
import note.utils.sms.SMSService;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static note.utils.UpYunUtil.testSync;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@RestController
@Data
@RequestMapping("/recordAndShare/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final RedisUtil redisUtil;
    private final SMSService smsService;
    private final EmailUtil emailUtil;

    String ok = "OK";

    /**
     * 登录
     *
     * @param user 用户米与密码
     * @return ok
     */
    @PostMapping("/login")
    public NoteResultUtil login(@RequestBody User user) {
        User userSql = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (userSql == null) {
            return NoteResultUtil.error("用户名不存在");
        }
        user.setPassword(String.valueOf(Md5Util.setMd5(user.getUsername(), user.getPassword())));
        if (userSql.getPassword().equals(user.getPassword())) {
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(userSql, userDto);
            userSql.setLastTime(new TimeUtil().getFormatDateForFive());
            userMapper.updateById(userSql);
            /*从缓存中找*/
            if (redisUtil.hsize(ConstantUtil.USER_TOKEN + user.getUsername()) != 0) {
                if (redisUtil.hasHkey(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername())) {
                    return NoteResultUtil.success(MapUtil.builder()
                            .put("UserInfo", userDto)
                            .put("Authorization", redisUtil.hget(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername()))
                            .map());
                }
            }
            // 存入缓存
            String token = JwtUtil.sign(user.getUsername(), user.getPassword());
            redisUtil.hset(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername(), token);
            redisUtil.expire(ConstantUtil.USER_TOKEN + user.getUsername(), 120 * 60);
            return NoteResultUtil.success(MapUtil.builder()
                    .put("UserInfo", userDto)
                    .put("Authorization", token)
                    .map()
            );
        }
        return NoteResultUtil.error(500, "密码错误");
    }

    /**
     * 退出登录
     *
     * @return ok
     */
    @GetMapping("/logout")
    public NoteResultUtil logout() {
        SecurityUtils.getSubject().logout();
        return NoteResultUtil.success();
    }

    /**
     * 注册
     *
     * @param user 用户名与密码
     * @return ok
     */
    @PostMapping("/signup")
    public NoteResultUtil signup(@RequestBody User user) {
        if (redisUtil.hsize(ConstantUtil.USER_NAME) != 0) {
            if (!redisUtil.hasHkey(ConstantUtil.USER_NAME, user.getUsername())) {
                userService.insUser(user);
                return NoteResultUtil.success();
            }
            return NoteResultUtil.error("用户已存在");
        } else {
            if (userService.selUser(user.getUsername()) < 1) {
                if (userService.insUser(user) > 0) {
                    return NoteResultUtil.success();
                }
            }
            return NoteResultUtil.error("注册失败");
        }
    }

    /**
     * 密码重置-查找用户
     *
     * @param username 用户名
     * @return 手机号
     */
    @GetMapping("/selUser")
    public NoteResultUtil selUser(String username) {
        if (redisUtil.hasHkey(ConstantUtil.USER_NAME, username)) {
            return NoteResultUtil.success(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone")));
        } else {
            if (userService.selUser(username) == 1) {
                return NoteResultUtil.success(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone")));
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
    @GetMapping("/sendSMSCode")
    public NoteResultUtil sendSmsCode(@RequestParam("username") String username) {
        String result = smsService.sendSms(String.valueOf(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone"))), 1);
        if (ok.equals(result)) {
            return NoteResultUtil.success();
        } else {
            return NoteResultUtil.error("获取验证码失败");
        }
    }

    /**
     * 密码重置 - 校验验证码
     *
     * @param username 用户名
     * @return 验证码
     */
    @GetMapping("/getSMSCode")
    public NoteResultUtil getSmsCode(@RequestParam("username") String username) {
        String phone = String.valueOf(userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("phone")));
        if (redisUtil.hasKey(ConstantUtil.USER_PHONE_CODE + phone)) {
            return NoteResultUtil.success(redisUtil.get(ConstantUtil.USER_PHONE_CODE + phone));
        } else {
            redisUtil.del(ConstantUtil.USER_PHONE_CODE + phone);
            return NoteResultUtil.error("验证码失效");
        }
    }

    /**
     * 密码重置 - 更新密码
     *
     * @param user username password
     * @return ""
     */
    @PostMapping("/updPassword")
    public NoteResultUtil updPassword(@RequestBody User user) {
        int i = userService.updPassword(user);
        if (i > 0) {
            return NoteResultUtil.success();
        }
        return NoteResultUtil.error("修改密码失败");
    }

    public NoteResultUtil delayToken(User user) {
        // todo jwt延期问题
        long remainingTime = 20 * 60;
        if (redisUtil.hasHkey(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername())) {
            if (redisUtil.ttl(ConstantUtil.USER_TOKEN + user.getUsername()) < remainingTime) {
                {
                    String newToken = JwtUtil.sign(user.getUsername(), user.getPassword());
                    redisUtil.hset(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername(), newToken);
                    redisUtil.expire(ConstantUtil.USER_TOKEN + user.getUsername(), 120 * 60);
                    return NoteResultUtil.success(newToken);
                }
            }
        }
        return NoteResultUtil.success(redisUtil.hget(ConstantUtil.USER_TOKEN + user.getUsername(), user.getUsername()));
    }

    /**
     * 更新头像
     *
     * @param file     头像
     * @param username 用户名
     * @return ok
     */
    @PostMapping("/updAvatar")
    public NoteResultUtil updAvatar(@RequestBody MultipartFile file, String username) throws Exception {
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
     * 更新用户信息
     */
    @PostMapping("/updUser")
    public NoteResultUtil updUser(@RequestBody User user) {
        return getNoteResultUtil(user);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return ok
     */
    @GetMapping("/sendEmail")
    public NoteResultUtil sendEmail(@RequestParam("email") String email) throws IOException, TemplateException, MessagingException {
        // 验证码随机数
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
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
     * 更新手机号码 - 发送验证码
     *
     * @param phone 手机号码
     * @return ok
     */
    @GetMapping("/sendSMS")
    public NoteResultUtil sendSms(@RequestParam("phone") String phone) {
        String result = smsService.sendSms(phone, 2);
        if (ok.equals(result)) {
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
    @PostMapping("/updUserPhone")
    public NoteResultUtil updUserPhone(@RequestBody User user) {
        if (redisUtil.hasKey(ConstantUtil.USER_PHONE_CODE + user.getPhone())) {
            return getNoteResultUtil(user);
        }
        return NoteResultUtil.error("验证码失效");
    }

    /**
     * 更新邮箱
     *
     * @param user 用户名 邮箱
     * @return 用户信息
     */
    @PostMapping("/updUserEmail")
    public NoteResultUtil updUserEmail(@RequestBody User user) {
        if (redisUtil.hasKey(ConstantUtil.USER_EMAIL + user.getEmail())) {
            return getNoteResultUtil(user);
        }
        return NoteResultUtil.error("验证码失效");
    }

    private NoteResultUtil getNoteResultUtil(@RequestBody User user) {
        userMapper.update(user, new QueryWrapper<User>().eq("username", user.getUsername()));
        User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user1, userDto);
        return NoteResultUtil.success(userDto);
    }
}