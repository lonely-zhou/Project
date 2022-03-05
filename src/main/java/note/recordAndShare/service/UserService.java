package note.recordAndShare.service;

import note.recordAndShare.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 查找用户是否存在
     *
     * @param username 用户名
     * @return 0 or 1
     */
    int selUser(String username);

    /**
     * 新增用户
     *
     * @param user 包含账户名与密码
     * @return 受影响条数
     */
    int insUser(User user);

    /**
     * 更新密码
     *
     * @param user username password
     * @return ""
     */
    int updPassword(User user);
}
