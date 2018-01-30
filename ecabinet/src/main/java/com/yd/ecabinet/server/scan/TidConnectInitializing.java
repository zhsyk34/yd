package com.yd.ecabinet.server.scan;

import com.clou.uhf.G3Lib.Enumeration.eGPI;
import com.clou.uhf.G3Lib.Enumeration.eTriggerCode;
import com.clou.uhf.G3Lib.Enumeration.eTriggerStart;
import com.clou.uhf.G3Lib.Enumeration.eTriggerStop;
import com.yd.ecabinet.config.RfidConfig;
import com.yd.ecabinet.rfid.ConnectInitializing;
import com.yd.ecabinet.server.OpenSignalListener;
import com.yd.rfid.RfidOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TidConnectInitializing implements ConnectInitializing {
    private final RfidConfig rfidConfig;

    private final RfidOperator rfidOperator;
    private final TidRepository tidRepository;

    private final OpenSignalListener openSignalListener;

    @Override
    public void afterConnect() {
        logger.info("正在初始化库存...");

        rfidOperator.readDuration(rfidConfig.getScan());

        tidRepository.init();

        logger.info("初始化库存完毕");

        rfidOperator.registerListener(eGPI._1, eTriggerStart.High_level, eTriggerCode.Four_Antenna_read_EPC_and_TID, eTriggerStop.Delay, 100);
        logger.info("已开启GPI触发事件回调读标签...");

        openSignalListener.start();
        logger.info("已监听服务器开门信息");
    }
}
