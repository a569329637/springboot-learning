package com.gsq.learning.netty.client;

import com.gsq.learning.netty.client.console.ConsoleManager;
import com.gsq.learning.netty.client.handler.*;
import com.gsq.learning.netty.codec.PacketDecoder;
import com.gsq.learning.netty.codec.PacketEncoder;
import com.gsq.learning.netty.codec.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author guishangquan
 * @date 2019-12-09
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int RETRY = 4;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    // 设置线程组
                    .group(workerGroup)
                    // 设置 io 模型
                    .channel(NioSocketChannel.class)
                    // 设置 worker 线程组 TCP 底层参数
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 设置 worker 线程组属性
                    .attr(AttributeKey.newInstance("boss_name"), "im_server_boss_thread")
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new Spliter());
                            ch.pipeline().addLast(new PacketDecoder());
                            ch.pipeline().addLast(new LoginResponseHandler());
                            ch.pipeline().addLast(new LogoutResponseHandler());
                            ch.pipeline().addLast(new PrivateChatResponseHandler());
                            ch.pipeline().addLast(new CreateGroupChatResponseHandler());
                            ch.pipeline().addLast(new JoinGroupChatResponseHandler());
                            ch.pipeline().addLast(new ExitGroupChatResponseHandler());
                            ch.pipeline().addLast(new GroupChatResponseHandler());
                            ch.pipeline().addLast(new ErrorMessageHandler());
                            ch.pipeline().addLast(new PacketEncoder());
                        }
                    });

            ChannelFuture future = connect(bootstrap, HOST, PORT, RETRY);

            // 把这里注释掉才会重连
            //future.sync();
            //future.channel().closeFuture().sync();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        } finally {
            //workerGroup.shutdownGracefully();
        }

    }

    private static ChannelFuture connect(Bootstrap bootstrap, String host, int port, int retry) {

        return bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " ===> 客户端启动成功，连接地址端口：" + host + ":" + port);
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println(new Date() + " ===> 客户端启动失败，重连次数已经用完");
            } else {
                int order = RETRY - retry + 1;
                int delay = 1 << order;
                System.out.println(new Date() + " ===> 客户端启动失败，正在尝试进行第[" + order + "]次重连 ......");
                bootstrap.config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });

    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            ConsoleManager consoleManager = new ConsoleManager();

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                consoleManager.exec(scanner, channel);
            }

        }).start();
    }
}
