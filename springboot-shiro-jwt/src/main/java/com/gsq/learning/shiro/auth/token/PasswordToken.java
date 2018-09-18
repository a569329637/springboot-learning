package com.gsq.learning.shiro.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author guishangquan
 * @date 2018/9/18
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
