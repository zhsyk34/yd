package com.yd.ecabinet.rfid;

import com.yd.ecabinet.service.OpenSignalListener;
import com.yd.ecabinet.service.PhpService;
import com.yd.ecabinet.service.StockService;
import com.yd.rfid.RfidMessageAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RfidMessageHandler extends RfidMessageAdapter {
    private final StockService stockService;
    private final PhpService phpService;
    private final OpenSignalListener openSignalListener;

    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int startOrStop) {
        logger.debug("电平状态:{}", gpiState);
        if (gpiState == 1) {
            logger.debug("检测到开门事件");
        } else {
            logger.debug("检测到关门事件,开始请求Python接口以提交订单...");
            this.submit();
        }
    }

    private void submit() {
        try {
            openSignalListener.lock();
            phpService.submitOrder(stockService.getDeltaStocks());
            openSignalListener.setNecessary(true);
        } finally {
            openSignalListener.unlock();
        }
    }
}
