package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.data.User;
import com.gsq.learning.netty.protocol.ENDEC;
import com.gsq.learning.netty.protocol.packet.LoginRequestPacket;
import com.gsq.learning.netty.protocol.packet.LoginResponsePacket;
import com.gsq.learning.netty.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        User user = DataBase.loginValidate(msg.getUsername(), msg.getPassword());

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (user != null) {
            loginResponsePacket.setUserId(user.getUserId());
            loginResponsePacket.setIsSuccess(true);

            SessionUtil.store(ctx.channel(), user, true);
            DataBase.login(user.getUserId(), ctx.channel());

            System.out.println(new Date() + " ===> 客户端：" + user.getUsername() + "(" + user.getUserId() + ")登录成功");
        } else {
            loginResponsePacket.setIsSuccess(false);

            System.out.println(new Date() + " ===> 客户端：" + msg.getUsername() + "登录失败");
        }

        ByteBuf byteBuf1 = Unpooled.buffer();
        ENDEC.INSTANCE.encode(loginResponsePacket, byteBuf1);
        ctx.channel().writeAndFlush(byteBuf1);
    }

}
