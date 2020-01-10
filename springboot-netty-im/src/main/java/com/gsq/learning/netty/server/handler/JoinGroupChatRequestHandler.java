package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.protocol.packet.JoinGroupChatRequestPacket;
import com.gsq.learning.netty.protocol.packet.JoinGroupChatResponsePacket;
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
public class JoinGroupChatRequestHandler extends SimpleChannelInboundHandler<JoinGroupChatRequestPacket> {

    public final static JoinGroupChatRequestHandler INSTANCE = new JoinGroupChatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupChatRequestPacket msg) throws Exception {

        ChannelGroup channelGroup = DataBase.getChannelGroup(msg.getGroupId());
        if (channelGroup == null) {
            ErrorUtil.sendErrorMessage(ctx.channel(), "群聊不存在");
            return;
        }

        channelGroup.add(ctx.channel());

        Session session = SessionUtil.get(ctx.channel());

        JoinGroupChatResponsePacket joinGroupChatResponsePacket = new JoinGroupChatResponsePacket();
        joinGroupChatResponsePacket.setGroupId(msg.getGroupId());
        joinGroupChatResponsePacket.setGroupName(channelGroup.name());
        joinGroupChatResponsePacket.setUsername(session.getUser().getUsername());
        channelGroup.writeAndFlush(joinGroupChatResponsePacket);
    }
}
