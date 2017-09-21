package com.yd.ecabinet.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.ecabinet.rfid.executor.AbstractDaemonService;
import com.yd.ecabinet.util.LoggerUtils;
import com.yd.ecabinet.util.ThreadUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yd.ecabinet.config.Config.TRY_INTERVAL;

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

        //SERVICE.scheduleAtFixedRate(this::watch, 0, RFID_SYNC, TimeUnit.MILLISECONDS);

        super.setStartup(true);
    }

    private void watch() {
        if (rfidOperator.isConnect()) {
            logger.info("RFID当前在线");
        } else {
            logger.error("RFID连接已断开,正在尝试重连...");

            rfidOperator.disconnect();
            ThreadUtils.await(TRY_INTERVAL);
            rfidOperator.connect(callback);
        }
    }

}
