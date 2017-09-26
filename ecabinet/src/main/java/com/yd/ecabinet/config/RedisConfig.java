package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

abstract class RedisConfig {
    static final String HOST;
    static final int PORT;

    static {
        HOST = PropUtils.getString("redis.host");
        PORT = PropUtils.getInt("redis.port");
    }
}
