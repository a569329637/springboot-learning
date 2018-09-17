package com.gsq.learning.shiro.auth.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义 session 工厂
 */
public class MySessionFactory implements SessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(MySessionFactory.class);

    @Override
    public Session createSession(SessionContext sessionContext) {
        MySession mySession = new MySession();
        logger.info("session = {}", mySession);
        if (null != sessionContext && sessionContext instanceof WebSessionContext) {
            WebSessionContext webSessionContext = (WebSessionContext) sessionContext;
            HttpServletRequest request = (HttpServletRequest) webSessionContext.getServletRequest();
            if (null != request) {
                mySession.setHost(request.getRemoteAddr());
                mySession.setUserAgent(request.getHeader("User-Agent"));
            }
        }
        return mySession;
    }
}
