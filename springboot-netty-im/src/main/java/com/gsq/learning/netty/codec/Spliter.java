package com.gsq.learning.netty.codec;

import com.gsq.learning.netty.protocol.ENDEC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Date;

/**
 * @author guishangquan
 * @date 2019-12-10
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static int lengthFieldOffset = 7;
    private static int lengthFieldLength = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        if (in.getInt(in.readerIndex()) != ENDEC.MAGIC_NUMBER) {

            System.out.println(new Date() + " ===> 收到非法的数据包");

            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }

}
