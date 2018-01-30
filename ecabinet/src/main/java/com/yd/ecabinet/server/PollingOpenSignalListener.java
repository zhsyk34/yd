package com.yd.ecabinet.server;

import com.yd.ecabinet.config.RfidConfig;
import com.yd.ecabinet.config.StoreConfig;
import com.yd.rfid.RfidOperator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollingOpenSignalListener implements OpenSignalListener {
    private final StoreConfig storeConfig;
    private final RfidConfig rfidConfig;

    private final RfidOperator rfidOperator;

    private final ScheduledExecutorService service;

    private final PhpService phpService;

    @Setter
    private volatile boolean necessary = true;

    @Override
    public void start() {
        service.scheduleWithFixedDelay(this::handle, storeConfig.getSignalInterval(), storeConfig.getSignalInterval(), MILLISECONDS);
    }

    @Override
    public void suspend() {
        this.necessary = false;
        logger.debug("暂停轮询----------------");
    }

    @Override
    public void resume() {
        this.necessary = true;
        logger.debug("继续轮询----------------");
    }

    private void handle() {
        try {
            if (this.necessary) {
                logger.debug("开始轮询----------------");
                boolean allowed = this.allowOpen();
                logger.info("是否允许开门:{}", allowed);

                if (allowed) {
                    rfidOperator.openAndClose(rfidConfig.getInterval());
                    this.necessary = false;
                }
            } else {
                logger.debug("当前不需要轮询-----------------");
            }
        } finally {
            logger.debug("结束轮询----------------");
        }
    }

    private boolean allowOpen() {
        //TODO
//        try {
//            Path path = Paths.get("C:\\Users\\Archimedes\\Desktop/state.txt");
//            String s = Files.readAllLines(path).get(0);
//            return "1".equals(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
        return "1".equals(phpService.queryOpenSignal());
    }

}
