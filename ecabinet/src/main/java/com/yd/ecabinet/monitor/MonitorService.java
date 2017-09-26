package com.yd.ecabinet.monitor;

import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.RfidMonitor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.yd.ecabinet.config.RfidConfig.SYNC;
import static com.yd.ecabinet.util.NetworkUtils.getHost;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class MonitorService {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2);

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RfidMonitor rfidMonitor;

    public MonitorService(@Autowired RfidMonitor rfidMonitor) {
        this.rfidMonitor = rfidMonitor;
    }

    public void watch() {
        SCHEDULER.scheduleWithFixedDelay(rfidMonitor::watch, SYNC, SYNC, SECONDS);
    }

    public void report() {
        SCHEDULER.scheduleAtFixedRate(() -> logger.info("当前服务器地址:{}", getHost()), SYNC, SYNC, SECONDS);
    }
}
