package note.recordAndShare.serviceImpl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Settings;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.SettingsMapper;
import note.recordAndShare.mapper.UserMapper;
import note.recordAndShare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.ConstantUtil;
import note.utils.Md5Util;
import note.utils.RedisUtil;
import note.utils.TimeUtil;
import org.springframework.stereotype.Service;

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


    /**
     * 查找用户是否存在
     *
     * @param username 用户名
     * @return 0 or 1
     */
    @Override
    public int selUser(String username) {
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
        user.setPassword(String.valueOf(Md5Util.setMd5(user.getUsername(), user.getPassword())));
        user.setLastTime(new TimeUtil().getFormatDateForFive());
        redisUtil.hset(ConstantUtil.USER_NAME, user.getUsername(), user.getUsername());
        Settings settings = new Settings();
        settings.setUserId(user.getId());
        settingsMapper.insert(settings);
        return userMapper.insert(user);
    }

    /**
     * 更新密码
     *
     * @param user username password
     * @return ""
     */
    @Override
    public int updPassword(User user) {
        user.setPassword(String.valueOf(Md5Util.setMd5(user.getUsername(), user.getPassword())));
        return userMapper.update(user, new UpdateWrapper<User>().eq("username", user.getUsername()));
    }
}
