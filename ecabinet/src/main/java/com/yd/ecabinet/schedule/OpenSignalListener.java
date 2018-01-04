//package com.yd.ecabinet.schedule;
//
//import com.yd.ecabinet.config.StoreConfig;
//import com.yd.ecabinet.util.HttpUtils;
//import com.yd.rfid.RfidOperator;
//import com.yd.rfid.util.ThreadUtils;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//import static com.yd.ecabinet.config.StoreConfig.INTERVAL;
//import static com.yd.ecabinet.config.StoreConfig.POLL;
//
//@Slf4j
//@Service
//
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class OpenSignalListener {
//
//    private final RfidOperator rfidOperator;
//
//    @Getter
//    private final Lock lock = new ReentrantLock();
//
//    @Setter
//    private volatile boolean necessary = true;
//
//    public void listen() {
//        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this::handle, INTERVAL, POLL, TimeUnit.MILLISECONDS);
//    }
//
//    private void handle() {
//        try {
//            lock.lock();
//            if (this.necessary) {
//                logger.debug("开始轮询----------------");
//                boolean allowed = allowOpen();
//                logger.error("是否允许开门:{}", allowed);
//
//                if (allowed) {
//                    this.executeOpen();
//                    this.necessary = false;
//                    logger.debug("结束轮询----------------");
//                }
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    private void executeOpen() {
//        while (!rfidOperator.isOpen()) {
//            rfidOperator.openDoor();
//        }
//        ThreadUtils.await(INTERVAL);
//        rfidOperator.closeDoor();
//    }
//
//    private boolean allowOpen() {
//        String uri = "http://www.estore.ai/upload/cabinet/" + StoreConfig.NUMBER + ".txt";
//        String r = HttpUtils.get(uri, null);
//        logger.error("查询结果:{}", r);
//        return "1".equals(r);
//    }
//
//}
