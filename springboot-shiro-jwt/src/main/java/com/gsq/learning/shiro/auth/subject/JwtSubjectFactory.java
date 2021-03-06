package com.gsq.learning.shiro.auth.subject;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author guishangquan
 * @date 2018/9/18
 */
public class JwtSubjectFactory extends DefaultWebSubjectFactory {

    private final DefaultSessionStorageEvaluator sessionStorageEvaluator;

    public JwtSubjectFactory(DefaultSessionStorageEvaluator sessionStorageEvaluator) {
        this.sessionStorageEvaluator = sessionStorageEvaluator;
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建 session
        context.setSessionCreationEnabled(Boolean.FALSE);
        this.sessionStorageEvaluator.setSessionStorageEnabled(Boolean.FALSE);

        // 覆盖之前的 WebDelegatingSubject，用 JwtSubject 代替
        if (!(context instanceof WebSubjectContext)) {
            return super.createSubject(context);
        }
        WebSubjectContext wsc = (WebSubjectContext) context;
        SecurityManager securityManager = wsc.resolveSecurityManager();
        Session session = wsc.resolveSession();
        boolean sessionEnabled = wsc.isSessionCreationEnabled();
        PrincipalCollection principals = wsc.resolvePrincipals();
        boolean authenticated = wsc.resolveAuthenticated();
        String host = wsc.resolveHost();
        ServletRequest request = wsc.resolveServletRequest();
        ServletResponse response = wsc.resolveServletResponse();

        return new JwtSubject(principals, authenticated, host, session, sessionEnabled,
                request, response, securityManager);
    }
}
