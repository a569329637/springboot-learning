package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.ErrorUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public final static AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel channel = ctx.channel();
        Session session = channel.attr(Session.SESSION_KEY).get();

        System.out.println("debug auth session = " + session);
        if (session == null || !session.isLogin()) {
            ErrorUtil.sendErrorMessage(channel, "当前用户没有登录");
            return;
        }

        super.channelRead(ctx, msg);
    }
}
