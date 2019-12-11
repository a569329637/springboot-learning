package com.gsq.learning.netty.protocol.command;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public interface Command {

    byte ERROR_MESSAGE = 0;

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;

    byte LOGOUT_REQUEST = 3;
    byte LOGOUT_RESPONSE = 4;

    byte PRIVATE_CHAT_REQUEST = 5;
    byte PRIVATE_CHAT_RESPONSE = 6;

    byte CREATE_GROUP_CHAT_REQUEST = 7;
    byte CREATE_GROUP_CHAT_RESPONSE = 8;

    byte JOIN_GROUP_CHAT_REQUEST = 9;
    byte JOIN_GROUP_CHAT_RESPONSE = 10;

    byte EXIT_GROUP_CHAT_REQUEST = 11;
    byte EXIT_GROUP_CHAT_RESPONSE = 12;

    byte GROUP_CHAT_REQUEST = 13;
    byte GROUP_CHAT_RESPONSE = 14;
}
