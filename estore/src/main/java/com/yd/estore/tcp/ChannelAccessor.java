package com.yd.estore.tcp;

import io.netty.channel.Channel;
import lombok.NonNull;

public interface ChannelAccessor {

    String ip(@NonNull Channel channel);

    int port(@NonNull Channel channel);

}
