package com.gsq.learning.netty.data;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class DataBase {

    private static Map<String, User> USERS;

    private static Map<String, Channel> onlineUserChannels;

    static {
        // 初始化用户
        USERS = new HashMap<>();
        for (int i = 0; i < 10; ++ i) {
            String username = "username" + i;
            String password = "123456";
            String userId = "uid" + i;
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setUserId(userId);
            USERS.put(user.getUsername(), user);
        }

        onlineUserChannels = new HashMap<>();

    }

    public static User loginValidate(String username, String password) {
        User user = USERS.get(username);
        if (user != null) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), password)) {
                return user;
            }
        }
        return null;
    }

    public static void login(String userId, Channel channel) {
        onlineUserChannels.put(userId, channel);
    }

    public static void logout(String userId) {
        onlineUserChannels.remove(userId);
    }

    public static Channel getOnlineUser(String userId) {
        return onlineUserChannels.get(userId);
    }

}
