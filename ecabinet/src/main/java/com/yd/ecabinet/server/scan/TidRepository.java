package com.yd.ecabinet.server.scan;

import lombok.NonNull;

import java.util.Collection;

public interface TidRepository {
    Collection<String> getOriginal();

    Collection<String> getCurrent();

    void init();

    void clearCurrent();

    void save(@NonNull String tid);

    Collection<String> getDelta();
}
