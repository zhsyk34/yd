package com.yd.ecabinet.server.scan;

import com.yd.ecabinet.config.RfidConfig;
import com.yd.ecabinet.config.StoreConfig;
import com.yd.ecabinet.domain.Orders;
import com.yd.ecabinet.server.AbstractDoorStateHandler;
import com.yd.ecabinet.server.OpenSignalListener;
import com.yd.ecabinet.server.PhpService;
import com.yd.ecabinet.util.JsonUtils;
import com.yd.rfid.util.ThreadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class RfidDoorStateHandler extends AbstractDoorStateHandler {
    private final StoreConfig storeConfig;
    private final RfidConfig rfidConfig;

    private final Lock scanLock = new ReentrantLock();
    private final Condition scanCondition = scanLock.newCondition();
    private final ScheduledExecutorService service;
    private final OpenSignalListener openSignalListener;
    private final TidRepository tidRepository;
    private final PhpService phpService;
    private volatile boolean scanFinished = false;

    @Override
    public void onClose() {
        super.onClose();

        openSignalListener.suspend();

        service.execute(this::submitOrders);
        service.execute(this::scanTid);
    }

    private void scanTid() {
        scanLock.lock();

        try {
            tidRepository.clearCurrent();

            logger.info("开始扫描标签<<<<<<<<<<<<<<<<<<<<");

            ThreadUtils.await(rfidConfig.getScan());

            logger.info("扫描标签完成>>>>>>>>>>>>>>>>>>>>>");

            scanFinished = true;

            scanCondition.signal();
        } finally {
            scanLock.unlock();
        }
    }

    private void submitOrders() {
        scanLock.lock();

        try {
            while (!scanFinished) {
                logger.info("正在等待标签扫描...");
                try {
                    scanCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Collection<String> delta = tidRepository.getDelta();
            Orders orders = Orders.fromTid(storeConfig.getNumber(), JsonUtils.toJson(delta));

            phpService.submitOrder(orders);
        } finally {
            openSignalListener.resume();
            scanLock.unlock();
        }
    }

}
