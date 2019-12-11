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
public class GroupChatResponsePacket implements Packet {

    private String groupName;
    private String groupId;
    private String username;
    private String message;

    @Override
    public byte getCommand() {
        return Command.GROUP_CHAT_RESPONSE;
    }
}
