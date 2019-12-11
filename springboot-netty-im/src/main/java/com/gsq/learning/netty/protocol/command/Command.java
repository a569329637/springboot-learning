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

    byte PRIVATE_REQUEST = 5;
    byte PRIVATE_RESPONSE = 6;

}
