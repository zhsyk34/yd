package com.yd.upload;

import com.yd.upload.monitor.MonitorService;
import com.yd.upload.rfid.RfidService;
import com.yd.upload.util.BeanFactory;
import com.yd.upload.util.LoggerUtils;
import org.slf4j.Logger;

public class Entry {

    private static final Logger logger = LoggerUtils.getLogger(Entry.class);

    public static void main(String[] args) throws InterruptedException {
        //connect rfid
        BeanFactory.getBean(RfidService.class).startup();

        //monitor status
        BeanFactory.getBean(MonitorService.class).watch();

        BeanFactory.destroy();

        logger.info("服务器已启动完毕");
    }

}
