package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.data.User;
import com.gsq.learning.netty.protocol.packet.PrivateChatRequestPacket;
import com.gsq.learning.netty.protocol.packet.PrivateChatResponsePacket;
import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.ErrorUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@ChannelHandler.Sharable
public class PrivateChatRequestHandler extends SimpleChannelInboundHandler<PrivateChatRequestPacket> {

    public final static PrivateChatRequestHandler INSTANCE = new PrivateChatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatRequestPacket msg) throws Exception {

        // 获取当前用户
        Session session = ctx.channel().attr(Session.SESSION_KEY).get();
        User user = session.getUser();

        System.out.println(new Date() + " ===> 收到用户[" + user.getUserId() + "]发给用户【" + msg.getToUserId() + "】的信息：" + msg.getMessage());

        // 查找在线用户
        Channel toUserChannel = DataBase.getOnlineUser(msg.getToUserId());
        if (toUserChannel == null) {
            ErrorUtil.sendErrorMessage(ctx.channel(), "对方用户不存在或不在线");
            return;
        }

        // 发送消息给用户
        PrivateChatResponsePacket privateChatResponsePacket = new PrivateChatResponsePacket();
        privateChatResponsePacket.setFromUserId(user.getUserId());
        privateChatResponsePacket.setMessage(msg.getMessage());
        toUserChannel.writeAndFlush(privateChatResponsePacket);
    }
}
