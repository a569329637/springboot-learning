package com.gsq.learning.netty.session;

import com.gsq.learning.netty.data.User;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
@Setter
@Getter
@ToString
public class Session {

    public static AttributeKey<Session> SESSION_KEY = AttributeKey.newInstance("session");

    private boolean isLogin;
    private User user;

}
