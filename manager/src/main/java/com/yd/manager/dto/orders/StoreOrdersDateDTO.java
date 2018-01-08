package com.yd.manager.dto.orders;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 具体店铺按日统计订单
 */
@Getter
public class StoreOrdersDateDTO extends OrdersDTO {
    private final String date;
    private final long storeId;

    public StoreOrdersDateDTO(String date, long storeId, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.date = date;
        this.storeId = storeId;
    }

    public static StoreOrdersDateDTO of(String date, long storeId) {
        return new StoreOrdersDateDTO(date, storeId, 0, null, null, null);
    }
}
