package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {

        if (msg.getIsSuccess()) {
            System.out.println(new Date() + " ===> 收到登录响应，登录成功，当前用户：" + msg.getUserId());
        } else {
            System.out.println(new Date() + " ===> 收到登录响应，登录失败");
        }
    }
}
