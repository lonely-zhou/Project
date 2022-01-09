package note.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import note.recordAndShare.entity.User;
import note.recordAndShare.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: shiro 自定义realm
 * @Date: 2022/1/8 下午 4:12
 **/

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取前端输入的username
        String username = usernamePasswordToken.getUsername();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            throw new UnknownAccountException("该用户不存在！");
        } else {
            //当前realm对象的name
            String realmName = getName();
            //盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
            return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, realmName);
        }
    }
}
