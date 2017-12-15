package com.yd.log.config;

public class LoggerConfig {

    public static final int SERVER_PORT;

    public static final String SAVE_DIR;
    public static final String CONFIG_FILE = "logback.xml";

    public static final String SUFFIX = ".log";

    public static final String CURRENT = "current";

    public static final String ROLL_PATTERN;
    public static final int ROLL_HISTORY;

    public static final String MSG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{100} - %msg%n";

    static final String PROFILE = "config.properties";

    static {
        SERVER_PORT = LoggerConfigParser.getInt("port");
        SAVE_DIR = LoggerConfigParser.getString("dir");
        ROLL_PATTERN = "%d{" + LoggerConfigParser.getString("roll.pattern") + "}";
        ROLL_HISTORY = LoggerConfigParser.getInt("roll.history");
    }
}
