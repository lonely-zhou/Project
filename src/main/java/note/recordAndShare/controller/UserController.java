package note.recordAndShare.controller;


import note.recordAndShare.entity.User;
import note.recordAndShare.utils.NoteResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-08 07:37:25
 */
@RestController
@RequestMapping("/recordAndShare/user")
public class UserController {

    @GetMapping("/login")
    public NoteResult login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            user.setPassword("");
            httpSession.setAttribute("user", user);
            return NoteResult.success();
        } catch (AuthenticationException e) {
            return NoteResult.errorMsg("输入的用户名或密码错误");
        }
    }
}
