package com.yd.ecabinet.server.script;

import com.yd.ecabinet.config.StoreConfig;
import com.yd.ecabinet.domain.Orders;
import com.yd.ecabinet.domain.Stock;
import com.yd.ecabinet.server.AbstractDoorStateHandler;
import com.yd.ecabinet.server.OpenSignalListener;
import com.yd.ecabinet.server.PhpService;
import com.yd.ecabinet.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class PythonDoorStateHandler extends AbstractDoorStateHandler {
    private final StoreConfig storeConfig;

    private final OpenSignalListener openSignalListener;

    private final PhpService phpService;
    private final StockService stockService;

    @Override
    public void onClose() {
        super.onClose();

        openSignalListener.suspend();

        phpService.submitOrder(this.calcOrders());

        openSignalListener.resume();
    }

    private Orders calcOrders() {
        Collection<Stock> stocks = stockService.getDeltaStocks();
        Map<String, Object> stockJson = stocks.stream().collect(toMap(Stock::getCode, Stock::getCount));
        return Orders.fromSpec(storeConfig.getNumber(), JsonUtils.toJson(stockJson));
    }
}
