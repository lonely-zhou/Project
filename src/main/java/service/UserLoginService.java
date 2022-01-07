package service;

import po.User;

public interface UserLoginService {
    User userLogin(String username, String password);

    void userRe(String username, String password);
}
