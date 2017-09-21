package com.yd.ecabinet.redis;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;

import java.util.Collection;

public interface RedisListenerElement {

    Collection<Topic> topics();

    MessageListener listener();
}
