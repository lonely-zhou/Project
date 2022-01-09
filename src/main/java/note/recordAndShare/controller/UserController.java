package note.recordAndShare.controller;


import lombok.Data;
import note.recordAndShare.entity.User;
import note.recordAndShare.service.UserService;
import note.recordAndShare.utils.NoteResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

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
@Data
@RequestMapping("/recordAndShare/user")
public class UserController {

    private final UserService userService;

    /*登录*/
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
            return NoteResult.errorMsg("用户名或密码错误");
        }
    }

    /*注册*/
    @PostMapping("/signup")
    public NoteResult signUp(@RequestBody User user) {
        if (userService.findUser(user.getUsername()) < 1) {
            /*插入数据 insert 成功返回受影响条数*/
            int insResultCode = userService.insUser(user);
            if (insResultCode > 0) return NoteResult.success();
        }
        return NoteResult.errorMsg("注册失败 账户已存在");
    }
}
