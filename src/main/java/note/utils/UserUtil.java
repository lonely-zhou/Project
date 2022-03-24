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

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    public static User selUser() {
        return userMapper.selectById(StpUtil.getExtra("user_id").toString());
    }
}
