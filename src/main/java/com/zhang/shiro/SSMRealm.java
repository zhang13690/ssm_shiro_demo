package com.zhang.shiro;

import com.zhang.domain.User;
import com.zhang.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class SSMRealm extends AuthorizingRealm {

    // 引用UserMapper
    @Resource
    private UserMapper userMapper;

    // 认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 先把token强换成UsernamePasswordToken类型以使用，本来就是这个类型。
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 2. 通过token中的账号（调用getUsername方法）查询用户信息
        User user = userMapper.getUserByAccount(upToken.getUsername());
        // 3. 判断user是否存在，存在的话就进行认证
        if (user == null) {
            return null;
        } else {
            // 认证。需要把需要的信息包装成AuthenticationInfo并返回
            // 这里使用SimpleAuthenticationInfo实现类
            // 其中有三个参数：
            // 参数1：签名。可传递任意对象，程序可以在任意位置访问当前放入的对象。这里就把User对象放进去，方便使用，就不需要自行通过session来保存登录的用户数据了。
            // 参数2：凭证。即把查询的密码传递进去即可。安全管理器负责比对。
            // 参数3：当前realm的名字，传递类名即可，为了区分应用中的不同realms，虽然本应用中只有一个realm。
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getSimpleName());
        }
    }

    // 授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 方法的返回值类型是AuthorizationInfo，即授权信息对象
        // 我们使用它的一个实现类SimpleAuthorizationInfo。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 调用addStringPermission方法进行授予权限
        info.addStringPermission("viewProducts"); // 为当前用户授予viewProducts权限
        // 调用addRole方法授予角色
        info.addRole("member"); // 为当前用户授予member角色。
        // 最后返回info即可
        return info;
    }
}
