package service.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.User;
import service.UserLoginService;

@Service("UserLoginService")
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserDao userDao;

    @Override
    public User userLogin(String username, String password) {
        return this.userDao.userLogin(username, password);
    }

    @Override
    public void userRe(String username, String password) {
        this.userDao.userRe(username, password);
    }
}
