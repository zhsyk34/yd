package com.yd.ecabinet.schedule;

import com.yd.ecabinet.config.StoreConfig;
import com.yd.rfid.RfidMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RfidMonitorService {

    private final RfidMonitor rfidMonitor;

    private final StoreConfig storeConfig;

    private final ScheduledExecutorService service;

    public void watch() {
        service.scheduleWithFixedDelay(rfidMonitor::watch, storeConfig.getSync(), storeConfig.getSync(), SECONDS);
    }

}
