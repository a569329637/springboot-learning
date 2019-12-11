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
public class ExitGroupChatRequestPacket implements Packet {

    private String groupId;

    @Override
    public byte getCommand() {
        return Command.EXIT_GROUP_CHAT_REQUEST;
    }
}
