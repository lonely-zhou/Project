package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import po.User;
import service.UserLoginService;

import javax.servlet.http.HttpSession;

@Controller
public class UserLogin {
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String userLogin(String username, String password, Model model, HttpSession session) {
        User user = this.userLoginService.userLogin(username, password);
        if (user != null) {
            session.setAttribute("UserSession", user);
            return "redirect:empList.do";
        } else {
            model.addAttribute("msg", "账号或密码错误，请重新输入！");
            return "login";
        }
    }

    @RequestMapping(value = "/loginRe.do", method = RequestMethod.POST)
    public String userRe(String username, String password, Model model) {
        User user = this.userLoginService.userLogin(username, password);
        if (("".equals(username) && "".equals(password)) || username.equals(user.getUsername())) {
            model.addAttribute("msg", "用户名重复--注册失败");
        } else {
            this.userLoginService.userRe(username, password);
            model.addAttribute("msg", "注册成功");
        }
        return "login";

    }

    @RequestMapping(value = "/logout.do")
    public String userOut(HttpSession session) {
        session.invalidate();
        return "redirect:login.do";
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }
}
