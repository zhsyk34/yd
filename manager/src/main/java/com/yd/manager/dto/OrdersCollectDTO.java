package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 店铺订单汇总信息
 */
@Data
public class OrdersCollectDTO {
    private final long storeId;
    private final String storeName;
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
