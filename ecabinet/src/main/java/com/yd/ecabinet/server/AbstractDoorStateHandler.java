package com.yd.ecabinet.server;

import com.yd.ecabinet.rfid.DoorStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractDoorStateHandler implements DoorStateHandler {
    @Override
    public void onOpen() {
        logger.debug("检测到开门事件");
    }

    @Override
    public void onClose() {
        logger.info("检测到关门事件");
    }
}
