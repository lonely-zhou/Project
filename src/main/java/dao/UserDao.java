package dao;

import org.apache.ibatis.annotations.Param;
import po.User;

public interface UserDao {
    User userLogin(@Param("username") String username, @Param("password") String password);

    void userRe(@Param("username") String username, @Param("password") String password);
}
