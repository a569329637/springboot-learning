package com.gsq.learning.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public interface Console {

    void exec(Scanner scanner, Channel channel);

}
