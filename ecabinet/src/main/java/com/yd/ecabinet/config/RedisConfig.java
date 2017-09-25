package com.yd.ecabinet.config;

import com.yd.ecabinet.util.PropUtils;

abstract class RedisConfig {
    static String REDIS_HOST;
    static int REDIS_PORT;

    static {
        REDIS_HOST = PropUtils.getString("redis.host");
        REDIS_PORT = PropUtils.getInt("redis.port");
    }
}
