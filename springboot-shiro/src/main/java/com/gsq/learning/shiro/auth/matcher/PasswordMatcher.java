package com.gsq.learning.shiro.auth.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义密码匹配规则
 */
public class PasswordMatcher implements CredentialsMatcher {

    private static final Logger logger = LoggerFactory.getLogger(PasswordMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (info instanceof SimpleAuthenticationInfo) {
            logger.info("salt = ", ((SimpleAuthenticationInfo) info).getCredentialsSalt().toBase64());
        }
        return token.getCredentials().toString().equals(info.getCredentials().toString()) &&
                token.getPrincipal().toString().equals(info.getPrincipals().toString());
    }
}
