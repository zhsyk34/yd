package com.yd.ecabinet.server;

import com.yd.ecabinet.domain.Orders;

public interface PhpService {
    String queryOpenSignal();

    void submitOrder(Orders orders);
}
