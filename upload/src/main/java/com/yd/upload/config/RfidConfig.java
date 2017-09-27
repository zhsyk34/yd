package com.yd.upload.config;

import com.yd.upload.util.PropUtils;

public abstract class RfidConfig {
    public static final int SYNC;
    static final String ID;
    static final int ANT;
    static final int RETRY = -1;

    static {
        ID = PropUtils.getString("rfid.id");
        ANT = PropUtils.getInt("rfid.ant");
        SYNC = PropUtils.getInt("rfid.sync");
    }
}
