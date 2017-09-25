package com.yd.estore;

import com.yd.estore.rfid.RfidService;
import com.yd.estore.tcp.TcpServer;
import com.yd.estore.udp.UdpServer;
import com.yd.estore.util.BeanFactory;
import com.yd.estore.util.LoggerUtils;
import org.slf4j.Logger;

public class Entry {

    private static final Logger logger = LoggerUtils.getLogger(Entry.class);

    public static void main(String[] args) {
        BeanFactory.getBean(TcpServer.class).startup();

        BeanFactory.getBean(UdpServer.class).startup();

        BeanFactory.getBean(RfidService.class).startup();

        BeanFactory.destroy();

        logger.info("服务器已启动完毕");
    }

}
