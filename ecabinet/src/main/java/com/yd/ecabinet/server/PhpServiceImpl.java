package com.yd.ecabinet.server;

import com.yd.ecabinet.config.StoreConfig;
import com.yd.ecabinet.domain.Orders;
import com.yd.ecabinet.util.HttpUtils;
import com.yd.ecabinet.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhpServiceImpl implements PhpService {
    private final StoreConfig storeConfig;

    @Override
    public String queryOpenSignal() {
        logger.debug("查询开门状态---------------------");
        String uri = storeConfig.getSignalUrl() + storeConfig.getNumber() + ".txt";
        return HttpUtils.get(uri);
    }

    @Override
    public void submitOrder(Orders orders) {
        String json = JsonUtils.toJson(orders);
        String result = HttpUtils.postForm(storeConfig.getOrderUrl(), JsonUtils.toMap(orders));
        logger.info("已向服务器提交订单:{},反馈结果:{}", json, result);
    }
}
