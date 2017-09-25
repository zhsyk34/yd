package com.yd.estore.tcp;

import com.yd.estore.config.Action;
import com.yd.estore.config.Command;
import com.yd.estore.config.StoreConfig;
import com.yd.estore.rfid.RfidService;
import com.yd.estore.util.JsonUtils;
import com.yd.estore.util.LoggerUtils;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.util.ThreadUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ChannelHandler.Sharable
final class TcpMessageHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RfidService rfidService;

    @Autowired
    TcpMessageHandler(RfidService rfidService) {
        this.rfidService = rfidService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ((msg instanceof String)) {
            String receive = (String) msg;
            logger.debug("接收到客户端请求:{}", receive);

            Command command = JsonUtils.parseJson(receive, Command.class);
            if (command == null) {
                logger.debug("非法的请求数据");
                return;
            }

            Optional.ofNullable(Action.parse(command.getAction())).ifPresent(action -> {
                RfidOperator operator = rfidService.getRfidOperator();

                switch (action) {
                    case ALWAYS_OPEN:
                        logger.info("请求长久开门");
                        operator.openDoor();
                        break;
                    case OPEN_AND_CLOSE:
                        operator.openDoor();

                        ThreadUtils.await(StoreConfig.STORE_INTERVAL);

                        operator.closeDoor();
                        break;
                    case TCP_HEARTBEAT:
                        ctx.channel().writeAndFlush(receive);
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
