package com.yd.ecabinet.redis;

import com.yd.ecabinet.config.Config;
import com.yd.ecabinet.rfid.RfidOperator;
import com.yd.ecabinet.util.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static com.yd.ecabinet.redis.RedisChannel.STORE;

@Component
public final class StoreMessageListenerElement extends AbstractRedisMessageListenerElement {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RfidOperator rfidOperator;

    public StoreMessageListenerElement(@Autowired RfidOperator rfidOperator) {
        super(STORE);
        this.rfidOperator = rfidOperator;
    }

    @Override
    void handleMessage(RedisChannel redisChannel, byte[] content) {
        if (redisChannel != STORE) {
            logger.error("错误的频道:{}", redisChannel);
            return;
        }

        String message = new String(content, StandardCharsets.UTF_8);
        logger.info("接收到{}上的信息{}", redisChannel.channel(), message);

        if (Config.STORE_SHOP.equals(message)) {
            logger.info("服务端请求开门");
            rfidOperator.openDoor();
        }
    }
}
