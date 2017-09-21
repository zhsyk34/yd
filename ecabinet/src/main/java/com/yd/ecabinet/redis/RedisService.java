package com.yd.ecabinet.redis;

import com.yd.ecabinet.util.LoggerUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class RedisService {

    @NonNull
    private final RedisMessageListenerContainer container;
    @NonNull
    private final List<RedisListenerElement> listenerElements;

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    @Autowired
    public RedisService(RedisMessageListenerContainer container, List<RedisListenerElement> listenerElements) {
        this.container = container;
        this.listenerElements = listenerElements;
    }

    public void monitor() {
        listenerElements.forEach(listenerElement -> {
            container.addMessageListener(listenerElement.listener(), listenerElement.topics());
            logger.info("已订阅redis频道:[{}]", listenerElement.topics().stream().map(Topic::getTopic).collect(Collectors.joining(",")));
        });
    }
}
