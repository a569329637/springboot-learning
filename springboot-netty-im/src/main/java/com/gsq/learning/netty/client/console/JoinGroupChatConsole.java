package com.gsq.learning.netty.client.console;

import com.gsq.learning.netty.protocol.packet.JoinGroupChatRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class JoinGroupChatConsole implements Console {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要加入群聊ID（格式：abcdef）：");
        String line = scanner.nextLine();
        String[] lines = line.split(" ");
        if (lines.length != 1) {
            System.out.println("输入要加入群聊ID格式错误");
            return;
        }

        JoinGroupChatRequestPacket joinGroupChatRequestPacket = new JoinGroupChatRequestPacket();
        joinGroupChatRequestPacket.setGroupId(lines[0]);
        channel.writeAndFlush(joinGroupChatRequestPacket);
    }
}
