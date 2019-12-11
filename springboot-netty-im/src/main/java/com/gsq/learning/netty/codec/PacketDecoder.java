package com.gsq.learning.netty.codec;

import com.gsq.learning.netty.protocol.ENDEC;
import com.gsq.learning.netty.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = ENDEC.INSTANCE.decode(in);
        out.add(packet);
    }
}
