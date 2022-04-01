package note.recordAndShare.service;

import note.recordAndShare.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import note.utils.NoteResultUtil;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
public interface RoleService extends IService<Role> {
    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    NoteResultUtil selAllRole();
}
