package com.gsq.learning.netty.server.handler;

import com.google.common.collect.Lists;
import com.gsq.learning.netty.data.DataBase;
import com.gsq.learning.netty.protocol.packet.CreateGroupChatRequestPacket;
import com.gsq.learning.netty.protocol.packet.CreateGroupChatResponsePacket;
import com.gsq.learning.netty.session.Session;
import com.gsq.learning.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-12-11
 */
public class CreateGroupChatRequestHandler extends SimpleChannelInboundHandler<CreateGroupChatRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupChatRequestPacket msg) throws Exception {
        List<String> usernameList = Lists.newArrayList();

        ChannelGroup channelGroup = new DefaultChannelGroup(msg.getGroupName(), GlobalEventExecutor.INSTANCE);
        msg.getUserIdList().forEach(userId -> {
            Channel channel = DataBase.getOnlineUser(userId);
            if (channel != null) {
                channelGroup.add(channel);

                Session session = SessionUtil.get(channel);
                usernameList.add(session.getUser().getUsername());
            }
        });
        String groupId = RandomStringUtils.randomAlphanumeric(6);
        DataBase.createChannelGroup(groupId, channelGroup);

        CreateGroupChatResponsePacket createGroupChatResponsePacket = new CreateGroupChatResponsePacket();
        createGroupChatResponsePacket.setGroupId(groupId);
        createGroupChatResponsePacket.setGroupName(channelGroup.name());
        createGroupChatResponsePacket.setUsernameList(usernameList);
        channelGroup.writeAndFlush(createGroupChatResponsePacket);
    }
}
