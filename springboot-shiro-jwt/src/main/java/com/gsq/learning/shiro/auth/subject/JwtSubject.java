package com.gsq.learning.shiro.auth.subject;

import com.gsq.learning.shiro.vo.JwtAccount;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtSubject extends WebDelegatingSubject {

    private JwtAccount jwtAccount;

    public JwtSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
                      ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, request, response, securityManager);
    }

    public JwtSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
                      boolean sessionEnabled, ServletRequest request, ServletResponse response,
                      SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }

    public JwtAccount getJwtAccount() {
        return jwtAccount;
    }

    public void setJwtAccount(JwtAccount jwtAccount) {
        this.jwtAccount = jwtAccount;
    }
}
