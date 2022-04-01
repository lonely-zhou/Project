package note.recordAndShare.service.impl;

import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Role;
import note.recordAndShare.mapper.RoleMapper;
import note.recordAndShare.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import note.utils.NoteResultUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-26 09:57:41
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;
    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    @Override
    public NoteResultUtil selAllRole() {
        return NoteResultUtil.success(roleMapper.selectList(null));
    }
}
