package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.ExitGroupChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class ExitGroupChatResponseHandler extends SimpleChannelInboundHandler<ExitGroupChatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupChatResponsePacket msg) throws Exception {
        System.out.println(new Date() + " ===> 收到退出群聊响应，群聊名：" + msg.getGroupName() + "[" + msg.getGroupId() + "]，退出成员：" + msg.getUsername());
    }
}
