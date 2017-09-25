package com.yd.rfid.executor;

import com.yd.rfid.util.ThreadUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class AbstractDaemonService implements DaemonService, Runnable {

    @Getter
    @Setter
    private volatile boolean startup = false;

    /**
     * 重试间隔时间
     */
    private final int interval;

    @Override
    public void startup() {
        EXECUTOR.submit(this);
        while (!this.isStartup()) {
            ThreadUtils.await(interval);
        }
    }

}
