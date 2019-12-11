package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.PrivateChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class PrivateChatResponseHandler extends SimpleChannelInboundHandler<PrivateChatResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatResponsePacket msg) throws Exception {

        System.out.println(new Date() + " ===> 收到用户[" + msg.getFromUserId() + "]发来的信息：" + msg.getMessage());

    }
}
