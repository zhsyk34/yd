package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 具体店铺按日统计订单
 */
@Data
public class StoreOrdersDateDTO {
    private final long storeId;
    private final String storeName;

    private final String date;

    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
