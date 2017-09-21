package com.yd.ecabinet.rfid.order;

import java.util.Set;

public interface TagProcessor {

    boolean isInitialized();

    void setInitialized(boolean initialized);

    void scan(long duration);

    void scan();

    /**
     * 统计剩余商品
     *
     * @param tid 商品编号
     */
    void statistics(String tid);

    Set<String> inventory();

    Set<String> remaining();

    Set<String> delta();

}
