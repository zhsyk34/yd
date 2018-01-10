package com.yd.ecabinet.service;

import com.yd.ecabinet.config.StoreConfig;
import com.yd.rfid.RfidOperator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenSignalListener {
    private final StoreConfig storeConfig;
    private final RfidOperator rfidOperator;
    private final ScheduledExecutorService service;

    private final PhpService phpService;

    private final Lock lock = new ReentrantLock();

    @Setter
    private volatile boolean necessary = true;

    public void listen() {
        service.scheduleWithFixedDelay(this::handle, storeConfig.getInterval(), storeConfig.getInterval(), SECONDS);
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    private void handle() {
        try {
            this.lock();
            if (this.necessary) {
                logger.debug("开始轮询----------------");
                boolean allowed = this.allowOpen();
                logger.info("是否允许开门:{}", allowed);

                if (allowed) {
                    rfidOperator.openAndClose();
                    this.necessary = false;
                }
            }
        } finally {
            logger.debug("结束轮询----------------");
            this.unlock();
        }
    }

    private boolean allowOpen() {
        return "1".equals(phpService.querySignal());
    }

}
