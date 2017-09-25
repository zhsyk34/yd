package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class RfidConfig {
    public static final String RFID_ID;
    public static final int RFID_ANT;
    public static final int RFID_SYNC;
    public static final int RFID_SCAN;
    public static final int RFID_RETRY;
    public static final int RFID_WAIT;

    static {
        RFID_ID = PropUtils.getString("rfid.id");
        RFID_ANT = PropUtils.getInt("rfid.ant");
        RFID_SYNC = PropUtils.getInt("rfid.sync");
        RFID_RETRY = PropUtils.getInt("rfid.retry");
        RFID_SCAN = PropUtils.getInt("rfid.scan");
        RFID_WAIT = PropUtils.getInt("rfid.wait");
    }
}
