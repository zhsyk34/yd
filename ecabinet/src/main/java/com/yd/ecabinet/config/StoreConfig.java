package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class StoreConfig {

    public static final String STORE_NAME;
    public static final String STORE_NUMBER;
    public static final int STORE_INTERVAL;
    public static final String STORE_SERVER;

    static {
        STORE_NAME = PropUtils.getString("store.name");
        STORE_NUMBER = PropUtils.getString("store.number");
        STORE_INTERVAL = PropUtils.getInt("store.interval");
        STORE_SERVER = PropUtils.getString("store.server");
    }

}
