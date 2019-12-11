package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.CreateGroupChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class CreateGroupChatResponseHandler extends SimpleChannelInboundHandler<CreateGroupChatResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupChatResponsePacket msg) throws Exception {
        String username = msg.getUsernameList().stream().reduce("", (username1, username2) -> username1 + "," + username2);
        System.out.println(new Date() + " ===> 收到创建群聊响应，群聊名：" + msg.getGroupName() + "[" + msg.getGroupId() + "]，成员有：" + username.substring(1));
    }
}
