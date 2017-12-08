package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户在店铺订单统计
 */
@Data
public class UserStoreOrdersDTO {
    private final long userId;
    private final String userName;

    private final long storeId;
    private final String storeName;

    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
