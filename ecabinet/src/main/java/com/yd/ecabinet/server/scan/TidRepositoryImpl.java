package com.yd.ecabinet.server.scan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TidRepositoryImpl implements TidRepository {
    @Getter
    private Collection<String> original = new HashSet<>();
    @Getter
    private Collection<String> current = new HashSet<>();

    @Override
    public void init() {
        this.reset();
        logger.info("共[{}] 件", this.original.size());
    }

    @Override
    public void clearCurrent() {
        this.current.clear();
    }

    @Override
    public void save(String tid) {
        current.add(tid);
    }

    @Override
    public Collection<String> getDelta() {
        logger.debug("原有库存 [{}] 件", original.size());
        logger.debug("剩余商品 [{}] 件", current.size());

        this.original.removeAll(this.current);
        Collection<String> delta = this.original;
        logger.debug("本次订单 [{}] 件", delta.size());

        this.reset();

        return delta;
    }

    private void reset() {
        this.original = this.current;
        this.current = null;
        this.current = new HashSet<>();
    }
}
