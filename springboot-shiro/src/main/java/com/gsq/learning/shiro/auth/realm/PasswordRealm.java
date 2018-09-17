package com.gsq.learning.shiro.auth.realm;

import com.gsq.learning.shiro.auth.token.PasswordToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户认证和授权
 */
public class PasswordRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "PasswordRealm";
    }

    @Override
    public Class getAuthenticationTokenClass() {
        // 只支持此 token
        return PasswordToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> roles = new HashSet<>();
        roles.add("role1");
        roles.add("role2");

        Set<String> permissions = new HashSet<>();
        permissions.add("user:create");
        permissions.add("user:update");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken.getPrincipal() == null || authenticationToken.getCredentials() == null) {
            throw new UnknownAccountException("用户名密码不能为空");
        }

        // find user by database
        Map<String, String> user = new HashMap<>();
        user.put("username", "admin");
        user.put("password", "123456");

        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配
        String username = user.get("username");
        String password = user.get("password");
        return new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes("salt".getBytes()), getName());
    }
}
