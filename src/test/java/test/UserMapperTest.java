package test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import note.RecordAndShareApplication;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description:
 * @Date: 2022/1/8 下午 10:42
 **/
@SpringBootTest(classes = RecordAndShareApplication.class)
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUser(){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username","lonelyzhou"));
        System.out.println(user);
    }

}