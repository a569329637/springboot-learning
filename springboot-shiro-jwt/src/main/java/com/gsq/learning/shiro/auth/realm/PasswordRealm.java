package com.gsq.learning.shiro.auth.realm;

import com.gsq.learning.shiro.auth.token.PasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2018/9/18
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
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof PasswordToken)) {
            return null;
        }
        PasswordToken passwordToken = (PasswordToken) token;
        if (StringUtils.isEmpty(passwordToken.getCredentials())
                || StringUtils.isEmpty(passwordToken.getPrincipal())) {
            throw new AuthenticationException("用户名密码不能为空");
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
