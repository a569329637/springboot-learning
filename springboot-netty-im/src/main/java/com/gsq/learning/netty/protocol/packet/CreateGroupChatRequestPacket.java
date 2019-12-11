package com.gsq.learning.netty.protocol.packet;

import com.gsq.learning.netty.protocol.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@Setter
@Getter
public class CreateGroupChatRequestPacket implements Packet {

    private String groupName;
    private List<String> userIdList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_CHAT_REQUEST;
    }
}
