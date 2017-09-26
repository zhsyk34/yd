package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class StoreConfig {

    public static final String NAME;
    public static final String NUMBER;
    public static final int INTERVAL;
    public static final String SERVER;

    static {
        NAME = PropUtils.getString("store.name");
        NUMBER = PropUtils.getString("store.number");
        INTERVAL = PropUtils.getInt("store.interval");
        SERVER = PropUtils.getString("store.server");
    }

}
