package com.gsq.learning.netty.codec;

import com.gsq.learning.netty.protocol.ENDEC;
import com.gsq.learning.netty.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        ENDEC.INSTANCE.encode(msg, out);
    }
}
