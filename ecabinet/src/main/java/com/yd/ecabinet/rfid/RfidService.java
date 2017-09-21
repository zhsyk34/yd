package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.rfid.executor.AbstractDaemonService;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.ecabinet.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.ecabinet.config.Config.RFID_SYNC;
import static com.yd.ecabinet.config.Config.TRY_INTERVAL;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
public class RfidService extends AbstractDaemonService {

    private final Logger logger = LoggerUtils.getLogger(this.getClass());

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    @Autowired
    public RfidService(IAsynchronousMessage callback, RfidOperator rfidOperator) {
        this.callback = callback;
        this.rfidOperator = rfidOperator;
    }

    @Override
    public void run() {
        rfidOperator.connect(callback);
        super.setStartup(true);
    }

    public void watch() {
        SERVICE.scheduleAtFixedRate(() -> {
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

}
