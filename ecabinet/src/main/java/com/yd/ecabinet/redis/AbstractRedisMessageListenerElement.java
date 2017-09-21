package com.yd.ecabinet.redis;

import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public abstract class AbstractRedisMessageListenerElement implements RedisListenerElement, MessageListener {

    @NonNull
    private final Collection<String> names;

    AbstractRedisMessageListenerElement(@NonNull RedisChannel... redisChannels) {
        this.names = Arrays.stream(redisChannels).map(RedisChannel::channel).collect(toList());
    }

    @Override
    public Collection<Topic> topics() {
        return names.stream().map(ChannelTopic::new).collect(toList());
    }

    @Override
    public MessageListener listener() {
        return this;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        Optional.ofNullable(RedisChannel.from(channel)).ifPresent(redisChannel -> handleMessage(redisChannel, message.getBody()));
    }

    abstract void handleMessage(RedisChannel redisChannel, byte[] content);

}
