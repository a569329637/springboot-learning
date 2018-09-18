package com.gsq.learning.shiro.vo;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtAccount implements Serializable {

    private static final long serialVersionUID = -895875540581785581L;

    private String tokenId;// 令牌id
    private String appId;// 客户标识（用户名、账号）
    private String issuer;// 签发者(JWT令牌此项有值)
    private Date issuedAt;// 签发时间
    private String audience;// 接收方(JWT令牌此项有值)
    private Set<String> roles;// 访问主张-角色(JWT令牌此项有值)
    private Set<String> perms;// 访问主张-资源(JWT令牌此项有值)
    private String host;// 客户地址

    public JwtAccount() {
    }

    public JwtAccount(String tokenId, String appId, String issuer, Date issuedAt, String audience, String roles, String perms, String host) {
        this.tokenId = tokenId;
        this.appId = appId;
        this.issuer = issuer;
        this.issuedAt = issuedAt;
        this.audience = audience;
        setRoles(roles);
        setPerms(perms);
        this.host = host;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setRoles(String roles) {
        this.roles = new HashSet<>();
        if (roles != null && !roles.equals("")) {
            String[] split = roles.split(",");
            this.roles.addAll(CollectionUtils.arrayToList(split));
        }
    }

    public void setPerms(String perms) {
        this.perms = new HashSet<>();
        if (perms != null && !perms.equals("")) {
            String[] split = perms.split(",");
            this.perms.addAll(CollectionUtils.arrayToList(split));
        }
    }
}
