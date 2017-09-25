package com.yd.ecabinet.monitor;

import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.RfidMonitor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.ecabinet.config.ExecutorFactory.SCHEDULER;
import static com.yd.ecabinet.config.RfidConfig.RFID_SYNC;
import static com.yd.ecabinet.util.NetworkUtils.getHost;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class MonitorService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RfidMonitor rfidMonitor;

    public MonitorService(@Autowired RfidMonitor rfidMonitor) {
        this.rfidMonitor = rfidMonitor;
    }

    public void watch() {
        SCHEDULER.scheduleWithFixedDelay(rfidMonitor::watch, RFID_SYNC, RFID_SYNC, MILLISECONDS);
    }

    public void report() {
        SCHEDULER.scheduleAtFixedRate(() -> logger.info("当前服务器地址:{}", getHost()), RFID_SYNC, RFID_SYNC, MILLISECONDS);
    }
}
