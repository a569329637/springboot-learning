package com.gsq.learning.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 对应 ServerHandler
 *
 * @author guishangquan
 * @date 2019-12-10
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String msg = "request message";
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(msg.getBytes());

        Channel channel = ctx.channel();
        channel.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String message = new String(bytes);
        System.out.println(new Date() + " ===> 收到服务端返回的消息，message=" + message);

    }
}
