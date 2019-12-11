package com.gsq.learning.netty.client.handler;

import com.gsq.learning.netty.protocol.packet.ErrorMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class ErrorMessageHandler extends SimpleChannelInboundHandler<ErrorMessagePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ErrorMessagePacket msg) throws Exception {
        System.out.println(new Date() + " ===> 指令错误，错误原因：" + msg.getErrorMessage());
    }
}
