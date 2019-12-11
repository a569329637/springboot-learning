package com.gsq.learning.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 对应 ClientHandler
 *
 * @author guishangquan
 * @date 2019-12-10
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String message = new String(bytes);
        System.out.println(new Date() + " ===> 收到客户端发来的消息，message=" + message);

        response(ctx);
    }

    private void response(ChannelHandlerContext ctx) {
        String msg = "response message";
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(msg.getBytes());

        Channel channel = ctx.channel();
        channel.writeAndFlush(byteBuf);
    }
}
