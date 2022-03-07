package note.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/3/6 下午 7:50
 **/
@Service
public class UserUtil {
    private static UserMapper userMapper;

    @Autowired
    public UserUtil(UserMapper userMapper) {
        UserUtil.userMapper = userMapper;
    }

    public static String selUserId() {
        String username = StpUtil.getLoginId().toString();
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username)).getId();
    }

    public static User selUser() {
        String username = StpUtil.getLoginId().toString();
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
