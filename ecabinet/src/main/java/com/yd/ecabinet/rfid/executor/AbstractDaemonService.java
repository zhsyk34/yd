package com.yd.ecabinet.rfid.executor;

import com.yd.ecabinet.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.yd.ecabinet.config.Config.TRY_INTERVAL;

public abstract class AbstractDaemonService implements DaemonService {

    public static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    @Getter
    @Setter
    private volatile boolean startup = false;

    @Override
    public void startup() {
        SERVICE.submit(this);
        while (!this.isStartup()) {
            ThreadUtils.await(TRY_INTERVAL);
        }
    }

}
