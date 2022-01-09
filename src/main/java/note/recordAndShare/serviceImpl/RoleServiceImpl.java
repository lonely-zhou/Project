package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.Role;
import note.recordAndShare.mapper.RoleMapper;
import note.recordAndShare.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-08 07:37:25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
