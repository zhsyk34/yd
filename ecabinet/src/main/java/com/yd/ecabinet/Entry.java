package com.yd.ecabinet;

import com.yd.ecabinet.monitor.MonitorService;
import com.yd.ecabinet.redis.RedisService;
import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.rfid.order.TagService;
import com.yd.ecabinet.util.BeanFactory;
import com.yd.ecabinet.util.LoggerUtils;
import org.slf4j.Logger;

public class Entry {

    private static final Logger logger = LoggerUtils.getLogger(Entry.class);

    public static void main(String[] args) throws InterruptedException {
        //connect rfid
        BeanFactory.getBean(RfidService.class).startup();

        //init tags
        BeanFactory.getBean(TagService.class).init();

        //subscribe redis message for open door by rfid
        BeanFactory.getBean(RedisService.class).monitor();

        MonitorService monitorService = BeanFactory.getBean(MonitorService.class);
        //monitor rfid status
        monitorService.watch();
        //report host message
        monitorService.report();

        BeanFactory.destroy();

        logger.info("服务器已启动完毕");
    }

}
