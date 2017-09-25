package com.yd.ecabinet.config;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.Entry;
import com.yd.rfid.DefaultRfidOperator;
import com.yd.rfid.RfidMonitor;
import com.yd.rfid.RfidOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static com.yd.ecabinet.config.RedisConfig.REDIS_HOST;
import static com.yd.ecabinet.config.RedisConfig.REDIS_PORT;
import static com.yd.ecabinet.config.RfidConfig.*;
import static com.yd.ecabinet.config.StoreConfig.STORE_INTERVAL;
import static com.yd.ecabinet.util.LoggerUtils.getLogger;

@Configuration
@ComponentScan(basePackageClasses = Entry.class)
public class SpringConfig {

    @Bean
    public RfidOperator rfidOperator() {
        return DefaultRfidOperator.instance(getLogger(RfidOperator.class), RFID_ID, RFID_ANT, STORE_INTERVAL, RFID_RETRY);
    }

    @Bean
    @Autowired
    public RfidMonitor rfidMonitor(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        return RfidMonitor.instance(getLogger(RfidMonitor.class), callback, rfidOperator, STORE_INTERVAL);
    }

    @Bean
    public JedisConnectionFactory factory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(REDIS_HOST);
        factory.setPort(REDIS_PORT);
        factory.setUsePool(true);

        return factory;
    }

    @Bean
    public RedisMessageListenerContainer container(@Autowired JedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);

        return container;
    }
}
