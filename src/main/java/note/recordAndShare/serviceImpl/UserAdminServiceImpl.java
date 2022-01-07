package note.recordAndShare.serviceImpl;

import note.recordAndShare.entity.UserAdmin;
import note.recordAndShare.mapper.UserAdminMapper;
import note.recordAndShare.service.UserAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lonelyzhou
 * @since 2022-01-07 02:33:54
 */
@Service
public class UserAdminServiceImpl extends ServiceImpl<UserAdminMapper, UserAdmin> implements UserAdminService {

}
