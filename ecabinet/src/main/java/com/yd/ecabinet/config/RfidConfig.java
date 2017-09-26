package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class RfidConfig {
    public static final int SYNC;
    public static final int SCAN;
    public static final int WAIT;
    static final String ID;
    static final int ANT;
    static final int RETRY;

    static {
        ID = PropUtils.getString("rfid.id");
        ANT = PropUtils.getInt("rfid.ant");
        SYNC = PropUtils.getInt("rfid.sync");
        RETRY = PropUtils.getInt("rfid.retry");
        SCAN = PropUtils.getInt("rfid.scan");
        WAIT = PropUtils.getInt("rfid.wait");
    }
}
