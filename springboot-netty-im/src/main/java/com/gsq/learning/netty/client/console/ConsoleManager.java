package com.gsq.learning.netty.client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class ConsoleManager implements Console {

    private Map<Integer, Console> consoleMap;

    public ConsoleManager() {
        consoleMap = new HashMap<>();
        consoleMap.put(1, new LoginConsole());
        consoleMap.put(2, new LogoutConsole());
        consoleMap.put(3, new PrivateChatConsole());
        consoleMap.put(4, new CreateGroupChatConsole());
        consoleMap.put(5, new JoinGroupChatConsole());
        consoleMap.put(6, new ExitGroupChatConsole());
        consoleMap.put(7, new GroupChatConsole());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("\n请输入命令：");
        System.out.println("1. 登录");
        System.out.println("2. 登出");
        System.out.println("3. 发送私聊消息");
        System.out.println("4. 创建群聊");
        System.out.println("5. 加入群聊");
        System.out.println("6. 退出群聊");
        System.out.println("7. 发送群聊消息");

        String line = scanner.nextLine();
        int command;
        try {
            command = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("请输入命令格式错误");
            return;
        }

        Console console = consoleMap.get(command);
        if (console == null) {
            System.out.println("请输入命令不存在");
            return;
        }

        console.exec(scanner, channel);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
