package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

public abstract class RedisConfig {
    public static final String HOST;
    public static final int PORT;

    static {
        HOST = PropUtils.getString("redis.host");
        PORT = PropUtils.getInt("redis.port");
    }
}
