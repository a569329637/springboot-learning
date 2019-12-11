package com.gsq.learning.netty.utils;

import com.gsq.learning.netty.protocol.packet.ErrorMessagePacket;
import io.netty.channel.Channel;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class ErrorUtil {

    public static void sendErrorMessage(Channel channel, String errorMessage) {
        ErrorMessagePacket errorMessagePacket = new ErrorMessagePacket();
        errorMessagePacket.setErrorMessage(errorMessage);
        channel.writeAndFlush(errorMessagePacket);
    }

}
