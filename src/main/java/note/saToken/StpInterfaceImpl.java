package note.saToken;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import note.recordAndShare.entity.Role;
import note.recordAndShare.mapper.RoleMapper;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Service;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 自定义权限验证接口扩展
 * @Date: 2022/3/8 下午 2:55
 **/
@Service
@Data
@Component
public class StpInterfaceImpl implements StpInterface {

    private final RoleMapper roleMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<String>();
        list.add("101");
        list.add("user-add");
        list.add("user-delete");
        list.add("user-update");
        list.add("user-get");
        list.add("article-get");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleMapper.selAllRole();
    }

}

