package com.yd.ecabinet.rfid.executor;

import com.yd.ecabinet.config.ExecutorFactory;
import com.yd.ecabinet.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;

import static com.yd.ecabinet.config.Config.TRY_INTERVAL;

public abstract class AbstractDaemonService implements DaemonService, Runnable {

    @Getter
    @Setter
    private volatile boolean startup = false;

    @Override
    public void startup() {
        ExecutorFactory.DAEMON_SERVICE.submit(this);
        while (!this.isStartup()) {
            ThreadUtils.await(TRY_INTERVAL);
        }
    }

}
