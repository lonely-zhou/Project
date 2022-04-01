package note.recordAndShare.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import lombok.Data;
import note.recordAndShare.service.RoleService;
import note.utils.NoteResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Data
@RestController
@SaCheckRole("su-admin")
@RequestMapping("/recordAndShare/role")
public class RoleController {
    private final RoleService roleService;

    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    @GetMapping("selAllRole")
    public NoteResultUtil selAllRole() {
        return roleService.selAllRole();
    }
}
