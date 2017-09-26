package com.yd.ecabinet.redis;

import com.yd.ecabinet.util.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Service
public final class RedisService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final RedisMessageListenerContainer container;
    private final List<RedisListenerElement> listenerElements;

    @Autowired
    public RedisService(RedisMessageListenerContainer container, List<RedisListenerElement> listenerElements) {
        this.container = container;
        this.listenerElements = listenerElements;
    }

    public void monitor() {
        listenerElements.forEach(listenerElement -> {
            container.addMessageListener(listenerElement.listener(), listenerElement.topics());
            logger.info("已订阅redis频道:[{}]", listenerElement.topics().stream().map(Topic::getTopic).collect(joining(",")));
        });
    }
}
