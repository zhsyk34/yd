package com.yd.ecabinet.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ExecutorFactory {

    public static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2);

}
