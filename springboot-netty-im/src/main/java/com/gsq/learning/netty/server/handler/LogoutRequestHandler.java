package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.data.User;
import com.gsq.learning.netty.protocol.packet.LogoutRequestPacket;
import com.gsq.learning.netty.protocol.packet.LogoutResponsePacket;
import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.ErrorUtil;
import com.gsq.learning.netty.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public final static LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        Session session = SessionUtil.get(ctx.channel());
        if (session == null || !session.isLogin() || session.getUser() == null) {
            ErrorUtil.sendErrorMessage(ctx.channel(), "当前用户不在线，不能重复退出登录");
            return;
        }

        User user = session.getUser();
        SessionUtil.store(ctx.channel(), null, false);
        DataBase.logout(user.getUserId());

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
