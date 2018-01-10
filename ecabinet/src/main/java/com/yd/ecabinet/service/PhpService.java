package com.yd.ecabinet.service;

import com.yd.ecabinet.config.StoreConfig;
import com.yd.ecabinet.domain.Orders;
import com.yd.ecabinet.domain.Stock;
import com.yd.ecabinet.util.HttpUtils;
import com.yd.ecabinet.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhpService {
    private static final String ORDERS_SERVER_URL = "http://www.estore.ai/api/order/cabinet/createOrder";
    private static final String SIGNAL_SERVER_URL = "http://www.estore.ai/upload/cabinet/";

    private final StoreConfig storeConfig;

    public String querySignal() {
        String uri = SIGNAL_SERVER_URL + storeConfig.getNumber() + ".txt";
        return HttpUtils.get(uri);
    }

    public void submitOrder(Collection<Stock> stocks) {
        Orders orders = this.ordersFrom(stocks);
        String json = JsonUtils.toJson(orders);
        String result = HttpUtils.postForm(ORDERS_SERVER_URL, JsonUtils.toMap(orders));
        logger.info("已向服务器提交订单:{},反馈结果:{}", json, result);
    }

    private Orders ordersFrom(Collection<Stock> stocks) {
        Map<String, Object> stockJson = stocks.stream().collect(toMap(Stock::getCode, Stock::getCount));
        return Orders.of(storeConfig.getNumber(), JsonUtils.toJson(stockJson));
    }

}
