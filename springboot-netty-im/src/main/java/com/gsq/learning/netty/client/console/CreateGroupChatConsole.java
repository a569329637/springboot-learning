package com.gsq.learning.netty.client.console;

import com.google.common.collect.Lists;
import com.gsq.learning.netty.protocol.packet.CreateGroupChatRequestPacket;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class CreateGroupChatConsole implements Console {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群聊名和成员用户ID列表（格式：群聊1 uid0 uid1）：");
        String line = scanner.nextLine();
        String[] lines = line.split(" ");
        if (lines.length <= 2) {
            System.out.println("群聊名和成员用户ID列表格式错误");
            return;
        }

        List<String> userIdList = Lists.newArrayList();
        for (int i = 1; i < lines.length; ++ i) {
            userIdList.add(lines[i]);
        }
        CreateGroupChatRequestPacket createGroupChatRequestPacket = new CreateGroupChatRequestPacket();
        createGroupChatRequestPacket.setGroupName(lines[0]);
        createGroupChatRequestPacket.setUserIdList(userIdList);
        channel.writeAndFlush(createGroupChatRequestPacket);
    }
}
