package com.gsq.learning.netty.utils;

import com.gsq.learning.netty.data.User;
import com.gsq.learning.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class SessionUtil {

    public static void store(Channel channel, User user, boolean isLogin) {
        Attribute<Session> attr = channel.attr(Session.SESSION_KEY);

        Session session = attr.get();
        if (session == null) {
            session = new Session();
        }
        session.setLogin(isLogin);
        session.setUser(user);
        attr.set(session);

        System.out.println("debug store session = " + session);
    }

    public static Session get(Channel channel) {
        Attribute<Session> attr = channel.attr(Session.SESSION_KEY);
        return attr.get();
    }

}
