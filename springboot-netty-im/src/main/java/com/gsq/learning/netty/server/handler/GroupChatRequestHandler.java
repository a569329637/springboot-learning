package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.protocol.packet.GroupChatRequestPacket;
import com.gsq.learning.netty.protocol.packet.GroupChatResponsePacket;
import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.ErrorUtil;
import com.gsq.learning.netty.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@ChannelHandler.Sharable
public class GroupChatRequestHandler extends SimpleChannelInboundHandler<GroupChatRequestPacket> {

    public final static GroupChatRequestHandler INSTANCE = new GroupChatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestPacket msg) throws Exception {

        ChannelGroup channelGroup = DataBase.getChannelGroup(msg.getGroupId());
        if (channelGroup == null) {
            ErrorUtil.sendErrorMessage(ctx.channel(), "群聊不存在");
            return;
        }

        Session session = SessionUtil.get(ctx.channel());

        GroupChatResponsePacket groupChatResponsePacket = new GroupChatResponsePacket();
        groupChatResponsePacket.setGroupId(msg.getGroupId());
        groupChatResponsePacket.setGroupName(channelGroup.name());
        groupChatResponsePacket.setUsername(session.getUser().getUsername());
        groupChatResponsePacket.setMessage(msg.getMessage());
        channelGroup.writeAndFlush(groupChatResponsePacket);
    }
}
