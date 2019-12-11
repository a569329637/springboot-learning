package com.gsq.learning.netty.client.console;

import com.gsq.learning.netty.protocol.packet.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class LogoutConsole implements Console {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
