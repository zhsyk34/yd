package com.yd.estore.tcp;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
final class TcpInitHandler extends ChannelInboundHandlerAdapter {

    private final TcpServerManager tcpServerManager;

    @Autowired
    TcpInitHandler(TcpServerManager tcpServerManager) {
        this.tcpServerManager = tcpServerManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        tcpServerManager.accept(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        tcpServerManager.close(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        tcpServerManager.error(ctx.channel(), cause);
    }

}
