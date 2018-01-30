package com.yd.ecabinet.server.script;

import com.yd.ecabinet.rfid.ConnectInitializing;
import com.yd.ecabinet.server.OpenSignalListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockConnectInitializing implements ConnectInitializing {
    private final StockService stockService;
    private final OpenSignalListener openSignalListener;

    @Override
    public void afterConnect() {
        stockService.init();

        openSignalListener.start();
        logger.info("已监听服务器开门信息");
    }
}
