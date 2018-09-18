package com.gsq.learning.shiro.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtToken implements AuthenticationToken {

    private String username;      //用户的标识
    private String ipHost;        //用户的IP
    private String deviceInfo;    //设备信息
    private String jwt;           //json web token值

    public JwtToken(String username, String ipHost, String deviceInfo, String jwt) {
        this.username = username;
        this.ipHost = ipHost;
        this.deviceInfo = deviceInfo;
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
