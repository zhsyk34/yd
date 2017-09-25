package com.yd.estore.tcp;

import com.yd.estore.util.CodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

final class TcpEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeBytes(Unpooled.wrappedBuffer(CodecUtils.encode(msg)));
    }

}
