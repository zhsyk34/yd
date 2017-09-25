package com.yd.estore.udp;

import com.yd.estore.config.Command;
import com.yd.estore.util.ByteUtils;
import com.yd.estore.util.JsonUtils;
import com.yd.estore.util.LoggerUtils;
import com.yd.estore.util.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Optional;

import static com.yd.estore.config.Action.NETWORK_REQUEST;
import static com.yd.estore.config.Action.parse;
import static com.yd.estore.config.NetworkConfig.TCP_HOST;
import static com.yd.estore.config.NetworkConfig.TCP_PORT;
import static com.yd.estore.util.JsonUtils.toJson;
import static io.netty.buffer.Unpooled.wrappedBuffer;

@Component
final class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        InetSocketAddress address = msg.sender();

        String receive = new String(ByteUtils.read(msg.content()), CharsetUtil.UTF_8);

        logger.debug("接收到客户端[{}] UDP 请求:{}", address, receive);

        Command command = JsonUtils.parseJson(receive, Command.class);
        if (command != null && parse(command.getAction()) == NETWORK_REQUEST) {
            logger.debug("响应客户端的网络信息请求");
            NetworkMessage networkMessage = MessageFactory.networkMessage(TCP_HOST, TCP_PORT);
            Optional.ofNullable(toJson(networkMessage)).ifPresent(message -> ctx.writeAndFlush(new DatagramPacket(wrappedBuffer(message.getBytes(CharsetUtil.UTF_8)), address)));
        }
    }
}
