package com.gsq.learning.shiro.auth.session;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * 自定义 session
 */
public class MySession extends SimpleSession {

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 在线状态
     */
    private OnlineStatus onlineStatus;

    public MySession() {
        super();
        this.onlineStatus = OnlineStatus.online;
    }

    public MySession(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public enum OnlineStatus {
        online("online", "在线"),
        offline("offline", "离线"),
        ;

        private final String name;
        private final String desc;

        private OnlineStatus(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }
}
