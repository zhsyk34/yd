package com.yd.ecabinet.rfid.order;

import com.yd.ecabinet.util.HttpUtils;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.yd.ecabinet.config.StoreConfig.STORE_INTERVAL;
import static com.yd.ecabinet.config.StoreConfig.STORE_SERVER;
import static com.yd.rfid.executor.DaemonService.EXECUTOR;

//TODO
@Service
public class TagService {

    private static final Lock lock = new ReentrantLock();
    private static final Condition finished = lock.newCondition();
    private static volatile boolean scan = false;
    private final Logger logger = LoggerUtils.getLogger(this.getClass());
    private final TagProcessor tagProcessor;

    public TagService(@Autowired TagProcessor tagProcessor) {
        this.tagProcessor = tagProcessor;
    }

    public void statistics(String tid) {
        tagProcessor.statistics(tid);
    }

    public void init() {
        logger.info("正在初始化库存商品...");

        EXECUTOR.execute(this::scan);

        while (!scan) {
            logger.info("正在初始化库存商品...");
            ThreadUtils.await(STORE_INTERVAL);
        }

        scan = false;

        tagProcessor.setInitialized(true);
        logger.info("库存商品已统计完毕,共[{}]件商品", tagProcessor.inventory().size());
    }

    private void scan() {
        lock.lock();

        try {
            tagProcessor.scan();

            scan = true;

            finished.signal();
        } finally {
            lock.unlock();
        }
    }

    private void submit() {
        if (!tagProcessor.isInitialized()) {
            return;
        }

        lock.lock();

        try {
            while (!scan) {
                logger.info("正在等待商品扫描...");
                try {
                    finished.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            scan = false;

            logger.info("准备创建订单");

            Map<String, Object> map = Order.toMap(tagProcessor.delta());
            String result = HttpUtils.postForm(STORE_SERVER, map);
            logger.info("已向服务器提交订单:{},反馈结果:{}", map.toString(), result);
        } finally {
            lock.unlock();
        }
    }

    public void process() {
        EXECUTOR.execute(this::scan);
        EXECUTOR.execute(this::submit);
    }
}
