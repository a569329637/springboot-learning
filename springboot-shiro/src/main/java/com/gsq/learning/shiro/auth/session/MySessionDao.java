package com.gsq.learning.shiro.auth.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session
 */
public class MySessionDao extends CachingSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(MySessionDao.class);

    private static final String SESSION_KEY_PREFIX = "Authorization-";

    private static final long SESSION_EXPIRE = 60 * 30;

    public static final Map<String, Session> memoryMap = new ConcurrentHashMap<>();

    @Override
    protected void doUpdate(Session session) {
        Serializable sessionId = session.getId();
        memoryMap.put(SESSION_KEY_PREFIX + sessionId.toString(), session);
        logger.info("更新 session, sessionId = {}, value = {}", sessionId, session);
    }

    @Override
    protected void doDelete(Session session) {
        // 不需要删除
        logger.info("删除 session, sessionId = {}, value = {}", session.getId(), session);
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        memoryMap.put(SESSION_KEY_PREFIX + sessionId.toString(), session);
        logger.info("创建 session, sessionId = {}, value = {}", sessionId, session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = memoryMap.get(SESSION_KEY_PREFIX + sessionId.toString());
        logger.info("读取 session, sessionId = {}, value = {}", sessionId, session);
        return session;
    }

    public void updateStatus(String sessionId, MySession.OnlineStatus status) {
        MySession mySession = (MySession) doReadSession(sessionId);
        if (null == mySession) {
            return;
        }
        mySession.setOnlineStatus(status);
        doUpdate(mySession);
    }
}
