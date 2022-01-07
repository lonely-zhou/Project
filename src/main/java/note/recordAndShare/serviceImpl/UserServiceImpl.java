package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-07 02:33:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
