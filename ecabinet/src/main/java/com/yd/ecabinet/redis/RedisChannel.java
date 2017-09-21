package com.yd.ecabinet.redis;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedisChannel {

    STORE("云柜");

    @NonNull
    private final String description;

    public static RedisChannel from(String channel) {
        for (RedisChannel redisChannel : values()) {
            if (redisChannel.channel().equals(channel)) {
                return redisChannel;
            }
        }
        return null;
    }

    public final String channel() {
        return this.name().replace("_", ":").toLowerCase();
    }

}
