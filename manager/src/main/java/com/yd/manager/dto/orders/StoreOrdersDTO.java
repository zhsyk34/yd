package com.yd.manager.dto.orders;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 店铺订单汇总信息
 */
@Getter
public class StoreOrdersDTO extends OrdersDTO {
    private final long storeId;
    private final String storeName;
    private final String storeAddress;

    public StoreOrdersDTO(long storeId, String storeName, String storeAddress, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }
}
