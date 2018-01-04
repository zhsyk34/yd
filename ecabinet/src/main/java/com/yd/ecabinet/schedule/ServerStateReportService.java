package com.yd.ecabinet.schedule;

import com.yd.ecabinet.config.StoreConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;

import static com.yd.ecabinet.util.LoggerUtils.getLogger;
import static com.yd.ecabinet.util.NetworkUtils.getHost;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServerStateReportService {

    private final StoreConfig storeConfig;

    private final ScheduledExecutorService service;

    private final Logger logger = getLogger(this.getClass());

    public void report() {
        service.scheduleAtFixedRate(() -> logger.info("当前服务器地址:{}", getHost()), storeConfig.getSync(), storeConfig.getSync(), SECONDS);
    }
}
