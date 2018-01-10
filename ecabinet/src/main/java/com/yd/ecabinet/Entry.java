package com.yd.ecabinet;

import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.service.OpenSignalListener;
import com.yd.ecabinet.service.RfidMonitorService;
import com.yd.ecabinet.util.BeanFactoryUtils;
import org.slf4j.LoggerFactory;

public class Entry {

    public static void main(String[] args) {
        BeanFactoryUtils.getBean(RfidService.class).startup();
        BeanFactoryUtils.getBean(RfidMonitorService.class).watch();

        BeanFactoryUtils.getBean(OpenSignalListener.class).listen();

//        BeanFactoryUtils.getBean(ServerStateReportService.class).report();

        LoggerFactory.getLogger(Entry.class).info("服务器已启动完毕");
    }

}
