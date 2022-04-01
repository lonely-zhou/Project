package note.recordAndShare.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import lombok.Data;
import note.recordAndShare.service.AdminService;
import note.recordAndShare.service.ReportNoteService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 管理员控制类
 * @Date: 2022/3/7 下午 5:25
 **/
@Data
@RestController
@SaCheckRole(value = {"admin", "su-admin"}, mode = SaMode.OR)
@RequestMapping("/recordAndShare/admin")
public class AdminController {
    private final AdminService adminService;
    private final ReportNoteService reportNoteService;

    /**
     * 分析页
     *
     * @return 用户数 笔记数 评论数
     */
    @GetMapping("selAnalysis")
    public NoteResultUtil selAnalysis() {
        return adminService.selAnalysis();
    }

    /**
     * 查询所有用户+权限
     *
     * @param page 分页
     * @return 所有用户
     */
    @GetMapping("selAllUser")
    public NoteResultUtil selAllUser(@RequestParam("page") Integer page) {
        return adminService.selAllUser(page);
    }

    /**
     * 修改用户角色
     *
     * @param rid 角色ID
     * @return ok
     */
    @GetMapping("updUserRole")
    public NoteResultUtil updUserRole(@RequestParam("rid") Integer rid, @RequestParam("id") String id) {
        return adminService.updUserRole(rid, id);
    }

    /**
     * 搜索用户
     *
     * @param page     分页
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("searchUser")
    public NoteResultUtil searchUser(@RequestParam("page") Integer page, @RequestParam("username") String username) {
        return adminService.searchUser(page, username);
    }

    /**
     * 搜索举报笔记
     *
     * @param page  分页
     * @param title 笔记标题
     * @return 笔记信息
     */
    @GetMapping("searchNote")
    public NoteResultUtil searchNote(@RequestParam("page") Integer page, @RequestParam("title") String title) {
        return reportNoteService.searchNote(page, title);
    }
}
