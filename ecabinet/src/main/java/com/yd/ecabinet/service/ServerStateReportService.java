package com.yd.ecabinet.service;

import com.yd.ecabinet.config.StoreConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;

import static com.yd.ecabinet.util.NetworkUtils.getHost;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerStateReportService {
    private final StoreConfig storeConfig;
    private final ScheduledExecutorService service;

    public void report() {
        service.scheduleAtFixedRate(() -> logger.info("当前服务器地址:{}", getHost()), storeConfig.getSync(), storeConfig.getSync(), SECONDS);
    }
}
