package com.yd.rfid;

import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.yd.rfid.util.ThreadUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor(staticName = "instance")
public class RfidMonitor {

    private final Logger logger;

    private final IAsynchronousMessage callback;

    private final RfidOperator rfidOperator;

    private final int interval;

    public void watch() {
        if (rfidOperator.isConnect()) {
            logger.info("RFID当前在线");
        } else {
            logger.error("RFID连接已断开,正在尝试重连...");

            rfidOperator.disconnect();

            ThreadUtils.await(interval);

            rfidOperator.connect(callback);
        }
    }

}
