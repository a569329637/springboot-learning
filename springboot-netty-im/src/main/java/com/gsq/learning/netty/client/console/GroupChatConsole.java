package com.gsq.learning.netty.client.console;

import com.gsq.learning.netty.protocol.packet.GroupChatRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class GroupChatConsole implements Console {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群聊信息（格式：abcdef hello）：");
        String line = scanner.nextLine();
        String[] lines = line.split(" ");
        if (lines.length != 2) {
            System.out.println("输入群聊信息格式错误");
            return;
        }

        GroupChatRequestPacket groupChatRequestPacket = new GroupChatRequestPacket();
        groupChatRequestPacket.setGroupId(lines[0]);
        groupChatRequestPacket.setMessage(lines[1]);
        channel.writeAndFlush(groupChatRequestPacket);
    }
}
