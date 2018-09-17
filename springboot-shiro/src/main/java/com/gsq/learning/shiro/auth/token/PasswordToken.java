package com.gsq.learning.shiro.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义 token
 */
public class PasswordToken implements AuthenticationToken {

    private String username;

    private String password;

    public PasswordToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
