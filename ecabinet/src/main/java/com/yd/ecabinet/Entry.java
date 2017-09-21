package com.yd.ecabinet;

import com.yd.ecabinet.redis.RedisService;
import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.rfid.order.TagService;
import com.yd.ecabinet.util.BeanFactory;
import com.yd.ecabinet.util.LoggerUtils;
import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

public class Entry {

    private static final Logger logger = LoggerUtils.getLogger(Entry.class);

    public static void main(String[] args) throws InterruptedException {
        RfidService rfidService = BeanFactory.getBean(RfidService.class);
        TagService tagService = BeanFactory.getBean(TagService.class);
        RedisService redisService = BeanFactory.getBean(RedisService.class);

        //connect rfid
        rfidService.startup();

        //init tags
        tagService.init();

        //monitor rfid status
        rfidService.watch();

        //subscribe redis message for open door by rfid
        redisService.monitor();

        BeanFactory.destroy();

        logger.info("服务器已启动完毕");

        new CountDownLatch(1).await();
    }

}
