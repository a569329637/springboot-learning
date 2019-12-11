package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.JoinGroupChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class JoinGroupChatResponseHandler extends SimpleChannelInboundHandler<JoinGroupChatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupChatResponsePacket msg) throws Exception {
        System.out.println(new Date() + " ===> 收到加入群聊响应，群聊名：" + msg.getGroupName() + "[" + msg.getGroupId() + "]，新加成员：" + msg.getUsername());
    }
}
