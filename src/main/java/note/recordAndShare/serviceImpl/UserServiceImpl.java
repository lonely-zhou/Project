package note.recordAndShare.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.recordAndShare.utils.TimeUtil;
import note.shiro.ShiroMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insUser(User user) {
        /*获取ID*/
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setRoleId(2);
        /*MD5加密*/
        Object obj = ShiroMD5.setMD5(user.getUsername(), user.getPassword());
        user.setPassword(String.valueOf(obj));
        String lastTime = new TimeUtil().getFormatDateForThree();
        user.setLastTime(lastTime);

        return userMapper.insert(user);
    }

    /*查找用户*/
    @Override
    public int findUser(String username) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("username", username)).intValue();
    }


}
