package com.yd.ecabinet;

import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.util.BeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Entry {

    public static void main(String[] args) {
        BeanFactoryUtils.getBean(RfidService.class).startup();
//        BeanFactoryUtils.getBean(RfidMonitorService.class).watch();

//        BeanFactoryUtils.getBean(ServerStateReportService.class).report();

        logger.info("服务器已启动完毕");
    }

}
