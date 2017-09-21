package com.yd.ecabinet.monitor;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.rfid.RfidOperator;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.ecabinet.util.NetworkUtils;
import com.yd.ecabinet.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.ecabinet.config.Config.RFID_SYNC;
import static com.yd.ecabinet.config.Config.TRY_INTERVAL;
import static com.yd.ecabinet.config.ExecutorFactory.SCHEDULED_SERVICE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class MonitorService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    @Autowired
    public MonitorService(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        this.callback = callback;
        this.rfidOperator = rfidOperator;
    }

    public void watch() {
        SCHEDULED_SERVICE.scheduleWithFixedDelay(() -> {
            if (rfidOperator.isConnect()) {
                logger.info("RFID当前在线");
            } else {
                logger.error("RFID连接已断开,正在尝试重连...");

                rfidOperator.disconnect();

                ThreadUtils.await(TRY_INTERVAL);

                rfidOperator.connect(callback);
            }
        }, RFID_SYNC, RFID_SYNC, MILLISECONDS);
    }

    public void report() {
        SCHEDULED_SERVICE.scheduleAtFixedRate(() -> logger.info("当前服务器地址:{}", NetworkUtils.getHost()), RFID_SYNC, RFID_SYNC, MILLISECONDS);
    }
}
