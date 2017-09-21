package com.yd.ecabinet.rfid.executor;

public interface DaemonService extends Runnable {

    boolean isStartup();

    void startup();
}
