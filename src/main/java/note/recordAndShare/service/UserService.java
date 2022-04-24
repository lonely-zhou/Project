package note.recordAndShare.service;

import com.upyun.UpException;
import freemarker.template.TemplateException;
import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.User;
import note.utils.NoteResultUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
public interface UserService extends IService<User> {
    /**
     * 用户是否存在
     *
     * @param username 用户名
     * @return 0 or 1
     */
    int isUser(String username);

    /**
     * 新增用户
     *
     * @param user 包含账户名与密码
     * @return 受影响条数
     */
    int insUser(User user);

    /**
     * 密码重置 - 更新密码
     *
     * @param user username password
     * @return ""
     */
    NoteResultUtil updPassword(User user);

    /**
     * 登录
     *
     * @param user      用户名与密码
     * @param autoLogin 自动登录
     * @param isAdmin   是否为管理员
     * @return ok
     */
    NoteResultUtil login(User user, boolean autoLogin, boolean isAdmin);

    /**
     * 是否登录
     *
     * @return 是否
     */
    NoteResultUtil isLogin();

    /**
     * 注册
     *
     * @param user 用户名与密码
     * @return ok
     */
    NoteResultUtil signup(User user);

    /**
     * 密码重置-查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    NoteResultUtil selUser(String username);

    /**
     * 密码重置 - 发送短信验证码
     *
     * @param username 用户名
     * @return ""
     */
    NoteResultUtil sendSmsCode(String username);

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return ok
     * @throws IOException        输入或输出异常
     * @throws TemplateException  加载和解析模板异常
     * @throws MessagingException 邮件发送异常
     */
    NoteResultUtil sendEmail(String email) throws IOException, TemplateException, MessagingException;

    /**
     * 更新邮箱
     *
     * @param user 用户名 邮箱
     * @return 用户信息
     */
    NoteResultUtil updUserEmail(User user);

    /**
     * 密码重置 - 校验验证码 - 手机号
     *
     * @param username 用户名
     * @return 验证码
     */
    NoteResultUtil getSmsCode(String username);

    /**
     * 密码重置 - 校验验证码 - 邮箱
     *
     * @param username 用户名
     * @return 验证码
     */
    NoteResultUtil getEmailCode(String username);

    /**
     * 更新手机号码 - 发送验证码
     *
     * @param phone 手机号码
     * @return ok
     */
    NoteResultUtil sendSms(String phone);

    /**
     * 更新手机号码
     *
     * @param user 用户名 手机号
     * @return 用户信息
     */
    NoteResultUtil updUserPhone(User user);

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
    NoteResultUtil updAvatar(MultipartFile file, String username) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;

    /**
     * 查询历史头像
     *
     * @return 头像URL
     * @throws UpException 文件上传异常
     * @throws IOException 输入或输出异常
     */
    NoteResultUtil selUserHistoricalAvatar() throws UpException, IOException;

    /**
     * 更新用户头像 -- 历史头像
     *
     * @param avatarUrl 头像url
     * @return ok
     */
    NoteResultUtil updSelAvatar(String avatarUrl);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    NoteResultUtil updUser(User user);

    /**
     * 删除用户
     * @param userId 用户id
     * @return ok
     */
    NoteResultUtil delUser(String userId);

}
