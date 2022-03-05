package note.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import note.recordAndShare.entity.Role;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.RoleMapper;
import note.recordAndShare.mapper.UserMapper;
import note.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 自定义 Realm
 * @Date: 2022/1/27 下午 12:46
 **/
@Service
@AllArgsConstructor
public class MyRealm extends AuthorizingRealm {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = JwtUtil.getUsername(principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        int rid = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).select("role_id")).getRoleId();
        String role = roleMapper.selectOne(new QueryWrapper<Role>().eq("rid",rid)).getRname();
        simpleAuthorizationInfo.addRole(role);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("用户名为空");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (!JwtUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("登录已过期");
        }
        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }
}
