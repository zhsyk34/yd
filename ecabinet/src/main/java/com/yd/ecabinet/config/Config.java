package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class Config {

    public static final int TRY_INTERVAL = 500;//ms

    public static String STORE_NAME;
    public static String STORE_SHOP;
    public static String STORE_SERVER;

    /*----------------------------rfid-config----------------------------*/
    public static String RFID_SN;
    public static int RFID_ANT;
    public static int RFID_SYNC;
    public static int RFID_TIMES;
    public static int RFID_SCAN;
    public static int RFID_WAIT;

    /*----------------------------redis-config----------------------------*/
    static String REDIS_HOST;
    static int REDIS_PORT;

    static {
        STORE_NAME = PropUtils.getString("store.name");
        STORE_SHOP = PropUtils.getString("store.shop");
        STORE_SERVER = PropUtils.getString("store.server");

        RFID_SN = PropUtils.getString("rfid.sn");
        RFID_ANT = PropUtils.getInt("rfid.ant");
        RFID_SYNC = PropUtils.getInt("rfid.sync") * 1000;
        RFID_TIMES = PropUtils.getInt("rfid.times");
        RFID_SCAN = PropUtils.getInt("rfid.scan") * 1000;
        RFID_WAIT = PropUtils.getInt("rfid.wait") * 1000;

        REDIS_HOST = PropUtils.getString("redis.host");
        REDIS_PORT = PropUtils.getInt("redis.port");
    }

}
