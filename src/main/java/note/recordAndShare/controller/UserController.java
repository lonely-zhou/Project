package note.recordAndShare.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.upyun.UpException;
import freemarker.template.TemplateException;
import lombok.Data;
import note.recordAndShare.entity.User;
import note.recordAndShare.service.UserService;
import note.utils.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

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

    private final UserService userService;

    /**
     * 登录
     *
     * @param user      用户名与密码
     * @param autoLogin 自动登录
     * @param isAdmin   是否为管理员
     * @return ok
     */
    @PostMapping("login")
    public NoteResultUtil login(@RequestBody User user, @RequestParam("autoLogin") boolean autoLogin,
                                @RequestParam(value = "isAdmin", required = false) boolean isAdmin) {
        return userService.login(user, autoLogin, isAdmin);
    }

    /**
     * 是否登录
     *
     * @return 是否
     */
    @GetMapping("isLogin")
    public NoteResultUtil isLogin() {
        return userService.isLogin();
    }

    /**
     * 退出登录
     *
     * @return ok
     */
    @GetMapping("logout")
    public NoteResultUtil logout() {
        StpUtil.logout();
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
        return userService.signup(user);
    }

    /**
     * 密码重置-查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("selUser")
    public NoteResultUtil selUser(@RequestParam("username") String username) {
        return userService.selUser(username);
    }

    /**
     * 密码重置 - 发送短信验证码
     *
     * @param username 用户名
     * @return ""
     */
    @GetMapping("/sendSMSCode")
    public NoteResultUtil sendSmsCode(@RequestParam("username") String username) {
        return userService.sendSmsCode(username);
    }

    /**
     * 密码重置 - 校验验证码 - 手机号
     *
     * @param username 用户名
     * @return 验证码
     */
    @GetMapping("/getSMSCode")
    public NoteResultUtil getSmsCode(@RequestParam("username") String username) {
        return userService.getSmsCode(username);
    }

    /**
     * 密码重置 - 校验验证码 - 邮箱
     *
     * @param username 用户名
     * @return 验证码
     */
    @GetMapping("getEmailCode")
    public NoteResultUtil getEmailCode(@RequestParam("username") String username) {
        return userService.getEmailCode(username);
    }

    /**
     * 更新手机号码
     *
     * @param user 用户名 手机号
     * @return 用户信息
     */
    @PostMapping("updPassword")
    public NoteResultUtil updPassword(@RequestBody User user) {
        return userService.updPassword(user);
    }

    /**
     * 更新头像
     *
     * @param file     头像文件
     * @param username 用户名
     * @return ok
     * @throws IOException              输入或输出异常
     * @throws NoSuchAlgorithmException 加密算法不可用
     * @throws SignatureException       签名异常
     * @throws InvalidKeyException      无效 Key
     */
    @PostMapping("/updAvatar")
    public NoteResultUtil updAvatar(@RequestBody MultipartFile file, String username) throws Exception {
        return userService.updAvatar(file, username);
    }

    /**
     * 更新用户头像 -- 历史头像
     *
     * @param avatarUrl 头像url
     * @return ok
     */
    @GetMapping("updSelAvatar")
    public NoteResultUtil updSelAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        return userService.updSelAvatar(avatarUrl);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @PostMapping("/updUser")
    public NoteResultUtil updUser(@RequestBody User user) {
        return userService.updUser(user);
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
    @GetMapping("/sendEmail")
    public NoteResultUtil sendEmail(@RequestParam("email") String email) throws IOException, TemplateException, MessagingException {
        return userService.sendEmail(email);
    }

    /**
     * 更新手机号码 - 发送验证码
     *
     * @param phone 手机号码
     * @return ok
     */
    @GetMapping("/sendSMS")
    public NoteResultUtil sendSms(@RequestParam("phone") String phone) {
        return userService.sendSms(phone);
    }

    /**
     * 更新手机号码
     *
     * @param user 用户名 手机号
     * @return 用户信息
     */
    @PostMapping("/updUserPhone")
    public NoteResultUtil updUserPhone(@RequestBody User user) {
        return userService.updUserPhone(user);
    }

    /**
     * 更新邮箱
     *
     * @param user 用户名 邮箱
     * @return 用户信息
     */
    @PostMapping("/updUserEmail")
    public NoteResultUtil updUserEmail(@RequestBody User user) {
        return userService.updUserEmail(user);
    }

    /**
     * 查询历史头像
     *
     * @return 头像URL
     * @throws UpException 文件上传异常
     * @throws IOException 输入或输出异常
     */
    @GetMapping("selUserHistoricalAvatar")
    public NoteResultUtil selUserHistoricalAvatar() throws UpException, IOException {
        return userService.selUserHistoricalAvatar();
    }

}