package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.GroupChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class GroupChatResponseHandler extends SimpleChannelInboundHandler<GroupChatResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponsePacket msg) throws Exception {
        System.out.println(new Date() + " ===> 收到群聊信息，群聊名：" + msg.getGroupName() + "[" + msg.getGroupId() + "]，成员[" + msg.getUsername() + "]发来信息：" + msg.getMessage());
    }
}
