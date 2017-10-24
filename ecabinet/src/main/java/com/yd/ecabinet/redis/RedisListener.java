package com.yd.ecabinet.redis;

import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.executor.AbstractDaemonService;
import com.yd.rfid.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Optional;

import static com.yd.ecabinet.config.RedisConfig.HOST;
import static com.yd.ecabinet.config.RedisConfig.PORT;
import static com.yd.ecabinet.config.RfidConfig.WAIT;
import static com.yd.ecabinet.config.StoreConfig.INTERVAL;

@Service
public class RedisListener extends AbstractDaemonService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final JedisPool jedisPool = new JedisPool(HOST, PORT);
    private final JedisPubSub jedisPubSub;

    public RedisListener(@Autowired JedisPubSub jedisPubSub) {
        super(INTERVAL);
        this.jedisPubSub = jedisPubSub;
    }

    @Override
    public void run() {
        try {
            this.subscribe();

            logger.info("redis订阅成功");
            super.setStartup(true);
        } catch (Exception e) {
            logger.error("redis出错", e);

            ThreadUtils.await(WAIT);

            logger.info("正在重连redis...");
            this.run();
        }
    }

    private void subscribe() throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
//            jedis.scriptLoad("CLIENT KILL TYPE pubsub");//TODO
            logger.info("开始监听redis频道[{}]", RedisChannel.STORE.channel());
            jedis.subscribe(jedisPubSub, RedisChannel.STORE.channel());
        } finally {
            Optional.ofNullable(jedis).ifPresent(Jedis::close);
        }
    }
}
