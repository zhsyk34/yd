package com.yd.log.config;

import com.yd.log.util.PropUtils;

public class Config {

    public static final int PORT;

    public static final String DIR;
    public static final String CONFIG_FILE = "logback.xml";

    public static final String SUFFIX = ".log";

    //file
    public static final String CURRENT = "current";

    //rollingPolicy
    public static final String ROLL_PATTERN = "%d{yyyy-MM-dd HH}";
    public static final int MAX_HISTORY = 24 * 60;

    //encoder
    public static final String MSG_PATTERN = "%d{HH:mm:ss} [%thread] %-5level %logger{100} - %msg%n";

    static {
        PORT = PropUtils.getInt("port");
        DIR = PropUtils.getString("dir");
    }
}
