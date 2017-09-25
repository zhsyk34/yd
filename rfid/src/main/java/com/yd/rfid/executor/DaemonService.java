package com.yd.rfid.executor;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public interface DaemonService {

    ExecutorService EXECUTOR = newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    boolean isStartup();

    void startup();
}
