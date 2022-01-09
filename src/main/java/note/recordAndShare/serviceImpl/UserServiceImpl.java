package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.User;
import note.recordAndShare.entity.vo.UserVO;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-08 07:37:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public UserVO findByUserName(String username) {
//        return userMapper.findByUserName(username);
//    }
}
