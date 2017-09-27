package com.yd.upload.config;

import com.yd.upload.util.PropUtils;

public abstract class StoreConfig {

    public static final String NAME;
    public static final int INTERVAL;
    public static final String SERVER;

    public static final String DEVICE_TYPE = "estore";
    public static String MAC = "000000000000";

    static {
        NAME = PropUtils.getString("store.name");
        INTERVAL = PropUtils.getInt("store.interval");
        SERVER = PropUtils.getString("store.server");
    }

}
