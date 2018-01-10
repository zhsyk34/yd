package com.yd.ecabinet;

import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.service.OpenSignalListener;
import com.yd.ecabinet.service.RfidMonitorService;
import com.yd.ecabinet.service.ServerStateReportService;
import com.yd.ecabinet.util.BeanFactoryUtils;
import org.slf4j.LoggerFactory;

public class Entry {

    public static void main(String[] args) {
        //connect rfid
        BeanFactoryUtils.getBean(RfidService.class).startup();

//        BeanFactoryUtils.getBean(TcpServer.class).startup();

        //monitor rfid status
        BeanFactoryUtils.getBean(RfidMonitorService.class).watch();

        BeanFactoryUtils.getBean(OpenSignalListener.class).listen();

        //report host status
        BeanFactoryUtils.getBean(ServerStateReportService.class).report();

        LoggerFactory.getLogger(Entry.class).info("服务器已启动完毕");
    }

}
