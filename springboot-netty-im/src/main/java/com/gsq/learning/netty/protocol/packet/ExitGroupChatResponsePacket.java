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
public class ExitGroupChatResponsePacket implements Packet {

    private String groupId;
    private String groupName;
    private String username;

    @Override
    public byte getCommand() {
        return Command.EXIT_GROUP_CHAT_RESPONSE;
    }
}
