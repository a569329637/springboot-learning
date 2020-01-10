package com.gsq.learning.netty.server.handler;

import com.gsq.learning.netty.protocol.command.Command;
import com.gsq.learning.netty.protocol.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guishangquan
 * @date 2020-01-10
 */
@ChannelHandler.Sharable
public class ImHandler extends SimpleChannelInboundHandler<Packet> {

    public final static ImHandler INSTANCE = new ImHandler();

    private Map<Byte, SimpleChannelInboundHandler> handlerMap;

    public ImHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.PRIVATE_CHAT_REQUEST, PrivateChatRequestHandler.INSTANCE);
        handlerMap.put(Command.CREATE_GROUP_CHAT_REQUEST, CreateGroupChatRequestHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP_CHAT_REQUEST, JoinGroupChatRequestHandler.INSTANCE);
        handlerMap.put(Command.EXIT_GROUP_CHAT_REQUEST, ExitGroupChatRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_CHAT_REQUEST, GroupChatRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
