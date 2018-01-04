package com.yd.ecabinet;

import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.schedule.RfidMonitorService;
import com.yd.ecabinet.schedule.ServerStateReportService;
import com.yd.ecabinet.tcp.TcpServer;
import com.yd.ecabinet.util.LocalBeanFactory;
import org.slf4j.LoggerFactory;

public class Entry {

    public static void main(String[] args) {
        //connect rfid
        LocalBeanFactory.getBean(RfidService.class).startup();

        LocalBeanFactory.getBean(TcpServer.class).startup();

        //monitor rfid status
        LocalBeanFactory.getBean(RfidMonitorService.class).watch();

        //report host status
        LocalBeanFactory.getBean(ServerStateReportService.class).report();

        LoggerFactory.getLogger(Entry.class).info("服务器已启动完毕");
    }

}
