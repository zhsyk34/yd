package com.yd.ecabinet.rfid.order;

import com.yd.ecabinet.rfid.RfidOperator;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.ecabinet.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.yd.ecabinet.config.Config.RFID_SCAN;

@Component
public class SimpleTagProcessor implements TagProcessor {

    private static Set<String> original = new HashSet<>();
    private static Set<String> current = new HashSet<>();

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RfidOperator rfidOperator;

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
        this.scan(RFID_SCAN);
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
        logger.debug("原有库存:\n{},共{}件商品", String.join(",", original),original.size());

        logger.debug("现存商品:\n{}", String.join(",", current));

        original.removeAll(current);

        Set<String> clone = new HashSet<>(original);

        original = current;
        current = new HashSet<>();

        logger.debug("订单商品:\n{},共{}件", String.join(",", clone), clone.size());

        return clone;
    }
}
