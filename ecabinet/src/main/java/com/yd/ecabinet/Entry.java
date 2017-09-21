package com.yd.ecabinet;

import com.yd.ecabinet.redis.RedisListener;
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
        rfidService.startup();

        TagService tagService = BeanFactory.getBean(TagService.class);
        tagService.init();

        BeanFactory.getBean(RedisListener.class).monitor();

        BeanFactory.destroy();

        logger.info("服务器已启动完毕");

        new CountDownLatch(1).await();
    }

}
