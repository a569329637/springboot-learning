package com.gsq.learning.netty.server;

import com.gsq.learning.netty.codec.PacketDecoder;
import com.gsq.learning.netty.codec.PacketEncoder;
import com.gsq.learning.netty.codec.Spliter;
import com.gsq.learning.netty.server.handler.AuthHandler;
import com.gsq.learning.netty.server.handler.LoginRequestHandler;
import com.gsq.learning.netty.server.handler.LogoutRequestHandler;
import com.gsq.learning.netty.server.handler.PrivateChatRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-09
 */
public class NettyServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    // 设置 react 模式
                    .group(bossGroup, workerGroup)
                    // 设置 io 模型
                    .channel(NioServerSocketChannel.class)
                    // 设置 boss 线程组 TCP 底层参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置 boss 线程组属性
                    .attr(AttributeKey.newInstance("boss_name"), "im_server_boss_thread")
                    // 设置 boss 线程组 channel 处理过程
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 设置 worker 线程组 TCP 底层参数
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置 worker 线程组属性
                    .childAttr(AttributeKey.newInstance("worker_name"), "im_server_worker_thread")
                    // 设置 worker 线程组 channel 处理过程
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new Spliter());
                            ch.pipeline().addLast(new PacketDecoder());
                            ch.pipeline().addLast(new LoginRequestHandler());
                            ch.pipeline().addLast(new LogoutRequestHandler());
                            ch.pipeline().addLast(new AuthHandler());
                            ch.pipeline().addLast(new PrivateChatRequestHandler());
                            ch.pipeline().addLast(new PacketEncoder());
                        }
                    });

            ChannelFuture future = bind(serverBootstrap, HOST, PORT);
            future.sync();

            // 相当于在这里阻塞，直到 server channel 关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    private static ChannelFuture bind(ServerBootstrap serverBootstrap, String host, int port) {
        return serverBootstrap.bind(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " ===> 服务端启动成功，绑定地址端口：" + host + ":" + port);
            } else {
                System.out.println(new Date() + " ===> 服务端启动绑定端口失败，正在重新绑定......");
                Thread.sleep(1000);
                bind(serverBootstrap, host, port);
            }
        });
    }

}
