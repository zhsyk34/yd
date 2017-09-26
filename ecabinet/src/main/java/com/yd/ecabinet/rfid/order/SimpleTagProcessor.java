package com.yd.ecabinet.rfid.order;

import com.yd.ecabinet.util.LoggerUtils;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.yd.ecabinet.config.RfidConfig.SCAN;

@Component
public class SimpleTagProcessor implements TagProcessor {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());
    private final RfidOperator rfidOperator;
    private Set<String> original = new HashSet<>();
    private Set<String> current = new HashSet<>();
    @Getter
    @Setter
    private volatile boolean initialized = false;

    public SimpleTagProcessor(@Autowired RfidOperator rfidOperator) {
        this.rfidOperator = rfidOperator;
    }

    @Override
    public void scan(long duration) {
        logger.info("标签扫描开始...");

        rfidOperator.startRead();

        ThreadUtils.await(duration);

        rfidOperator.stopRead();

        logger.info("标签扫描结束");
    }

    @Override
    public void scan() {
        this.scan(SCAN);
    }

    @Override
    public void statistics(String tid) {
        if (this.isInitialized()) {
            current.add(tid);
        } else {
            original.add(tid);
        }
    }

    @Override
    public Set<String> inventory() {
        return Collections.unmodifiableSet(original);
    }

    @Override
    public Set<String> remaining() {
        return Collections.unmodifiableSet(current);
    }

    @Override
    public Set<String> delta() {
        logger.debug("原有库存共 [{}] 件", original.size());
        logger.debug("剩余商品共 [{}] 件", current.size());

        original.removeAll(current);

        Set<String> clone = new HashSet<>(original);

        original = current;
        current = new HashSet<>();

        logger.debug("本次订单商品共 [{}] 件:{}", clone.size(), String.join(",", clone));

        return clone;
    }
}
