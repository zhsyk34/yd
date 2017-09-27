package com.yd.upload.monitor;

import com.yd.rfid.RfidMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.upload.config.RfidConfig.SYNC;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class MonitorService {

    private final RfidMonitor rfidMonitor;

    public MonitorService(@Autowired RfidMonitor rfidMonitor) {
        this.rfidMonitor = rfidMonitor;
    }

    public void watch() {
        newSingleThreadScheduledExecutor().scheduleWithFixedDelay(rfidMonitor::watch, SYNC, SYNC, SECONDS);
    }

}
