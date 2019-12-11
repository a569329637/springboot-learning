package com.gsq.learning.netty.protocol.packet;

import com.gsq.learning.netty.protocol.command.Command;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class LogoutRequestPacket implements Packet {

    @Override
    public byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
