package com.yd.ecabinet;

import com.yd.ecabinet.rfid.RfidService;
import com.yd.ecabinet.schedule.RfidMonitorService;
import com.yd.ecabinet.schedule.ServerStateReportService;
import com.yd.ecabinet.tcp.TcpServer;
import com.yd.ecabinet.util.BeanFactory;
import com.yd.ecabinet.util.LoggerUtils;

public class Entry {

    public static void main(String[] args) {
        //connect rfid
        BeanFactory.getBean(RfidService.class).startup();

        BeanFactory.getBean(TcpServer.class).startup();

        //monitor rfid status
        BeanFactory.getBean(RfidMonitorService.class).watch();

        //report host status
        BeanFactory.getBean(ServerStateReportService.class).report();

        LoggerUtils.getLogger(Entry.class).info("服务器已启动完毕");
    }

}
