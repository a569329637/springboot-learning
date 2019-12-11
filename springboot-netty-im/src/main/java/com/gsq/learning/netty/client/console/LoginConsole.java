package com.gsq.learning.netty.client.console;

import com.gsq.learning.netty.protocol.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class LoginConsole implements Console {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户名和密码（格式：username0 123456）：");
        String line = scanner.nextLine();
        String[] lines = line.split(" ");
        if (lines.length != 2) {
            System.out.println("输入用户名和密码格式错误");
            return;
        }

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername(lines[0]);
        loginRequestPacket.setPassword(lines[1]);
        channel.writeAndFlush(loginRequestPacket);
    }
}
