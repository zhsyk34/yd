package com.yd.estore.config;

import com.yd.estore.util.PropUtils;

public abstract class RfidConfig {
    public static final String RFID_ID;
    public static final int RFID_CLEAN;
    static final int RFID_ANT;
    static final int RFID_RETRY;
    static final int RFID_SYNC;//TODO

    static {
        RFID_ID = PropUtils.getString("rfid.id");
        RFID_ANT = PropUtils.getInt("rfid.ant");

        RFID_RETRY = PropUtils.getInt("rfid.retry");
        RFID_CLEAN = PropUtils.getInt("rfid.clean");
        RFID_SYNC = PropUtils.getInt("rfid.sync");
    }
}
