package com.yd.estore.config;

import com.yd.estore.util.PropUtils;

public abstract class StoreConfig {

    public static String STORE_NAME;
    public static int STORE_INTERVAL;

    static {
        STORE_NAME = PropUtils.getString("store.name");
        STORE_INTERVAL = PropUtils.getInt("store.interval");
    }

}
