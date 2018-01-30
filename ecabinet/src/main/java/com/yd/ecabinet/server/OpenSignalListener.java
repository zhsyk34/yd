package com.yd.ecabinet.server;

public interface OpenSignalListener {
    void start();

    void suspend();

    void resume();
}
