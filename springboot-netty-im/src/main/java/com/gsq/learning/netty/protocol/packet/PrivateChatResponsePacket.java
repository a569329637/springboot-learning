package com.gsq.learning.netty.protocol.packet;

import com.gsq.learning.netty.protocol.command.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@Setter
@Getter
public class PrivateChatResponsePacket implements Packet {

    private String fromUserId;
    private String message;

    @Override
    public byte getCommand() {
        return Command.PRIVATE_CHAT_RESPONSE;
    }
}
