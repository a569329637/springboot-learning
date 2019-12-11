package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.protocol.packet.ExitGroupChatRequestPacket;
import com.gsq.learning.netty.protocol.packet.ExitGroupChatResponsePacket;
import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.ErrorUtil;
import com.gsq.learning.netty.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class ExitGroupChatRequestHandler extends SimpleChannelInboundHandler<ExitGroupChatRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupChatRequestPacket msg) throws Exception {

        ChannelGroup channelGroup = DataBase.getChannelGroup(msg.getGroupId());
        if (channelGroup == null) {
            ErrorUtil.sendErrorMessage(ctx.channel(), "群聊不存在");
            return;
        }

        channelGroup.remove(ctx.channel());

        Session session = SessionUtil.get(ctx.channel());

        ExitGroupChatResponsePacket exitGroupChatResponsePacket = new ExitGroupChatResponsePacket();
        exitGroupChatResponsePacket.setGroupId(msg.getGroupId());
        exitGroupChatResponsePacket.setGroupName(channelGroup.name());
        exitGroupChatResponsePacket.setUsername(session.getUser().getUsername());
        channelGroup.writeAndFlush(exitGroupChatResponsePacket);
    }
}
