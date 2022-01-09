package note.recordAndShare.service;

import note.recordAndShare.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import note.recordAndShare.entity.vo.UserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-08 07:37:25
 */
public interface UserService extends IService<User> {
    int insUser(User user);
    int findUser(String username);
}
