package com.yd.estore.udp;

import com.yd.estore.config.NetworkConfig;
import com.yd.estore.util.LoggerUtils;
import com.yd.rfid.executor.AbstractDaemonService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.estore.config.StoreConfig.STORE_INTERVAL;

@Service
public final class UdpServer extends AbstractDaemonService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final UdpHandler udpHandler;

    public UdpServer(@Autowired UdpHandler udpHandler) {
        super(STORE_INTERVAL);
        this.udpHandler = udpHandler;
    }

    @Override
    public void run() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group).channel(NioDatagramChannel.class);
            bootstrap.option(ChannelOption.SO_BROADCAST, true);
            bootstrap.handler(new ChannelInitializer<DatagramChannel>() {
                @Override
                protected void initChannel(DatagramChannel ch) throws Exception {
                    ch.pipeline().addLast(udpHandler);
                }
            });

            Channel channel = bootstrap.bind(NetworkConfig.UDP_PORT).syncUninterruptibly().channel();

            logger.info("{}[{}:{}]启动成功", this.getClass().getSimpleName(), NetworkConfig.TCP_HOST, NetworkConfig.UDP_PORT);
            super.setStartup(true);

            channel.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
