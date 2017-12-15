package com.yd.apk.constant;

import com.yd.apk.util.PropUtils;

public class Config {

    public static final String DIR;
    public static final String SUFFIX;

    static {
        DIR = PropUtils.getString("dir");
        SUFFIX = PropUtils.getString("suffix");
    }

}
