package com.gsq.learning.shiro.auth.matcher;

import com.gsq.learning.shiro.auth.subject.JwtSubject;
import com.gsq.learning.shiro.utils.JsonWebTokenUtil;
import com.gsq.learning.shiro.vo.JwtAccount;
import io.jsonwebtoken.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.Subject;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String jwt = (String) info.getCredentials();
        JwtAccount jwtAccount;
        try{
            jwtAccount = JsonWebTokenUtil.parseJwt(jwt, JsonWebTokenUtil.SECRET_KEY);
        } catch(SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e){
            throw new AuthenticationException("error jwt");
        } catch(ExpiredJwtException e){
            throw new AuthenticationException("expired jwt");
        } catch(Exception e){
            throw new AuthenticationException("error jwt");
        }
        if (null == jwtAccount) {
            throw new AuthenticationException("error jwt");
        }
        // 如果「JWT」过期了就不会执行到这里
        Subject subject = SecurityUtils.getSubject();
        if (subject instanceof JwtSubject) {
            ((JwtSubject) subject).setJwtAccount(jwtAccount);
        }
        return true;
    }
}
