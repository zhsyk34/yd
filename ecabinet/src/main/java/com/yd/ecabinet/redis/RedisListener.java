package com.yd.ecabinet.redis;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class RedisListener {

    @NonNull
    private final RedisMessageListenerContainer container;
    @NonNull
    private final List<RedisListenerElement> elements;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RedisListener(RedisMessageListenerContainer container, List<RedisListenerElement> elements) {
        this.container = container;
        this.elements = elements;
    }

    public void monitor() {
        elements.forEach(element -> {
            container.addMessageListener(element.listener(), element.topics());
            logger.info("已订阅redis频道:[{}]", element.topics().stream().map(Topic::getTopic).collect(Collectors.joining(",")));
        });
    }
}
