package com.yd.manager.dto.orders;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 用户在店铺订单统计
 */
@Getter
public class UserStoreOrdersDTO extends OrdersDTO {
    private final long userId;
    private final String userName;

    private final long storeId;
    private final String storeName;

    public UserStoreOrdersDTO(long userId, String userName, long storeId, String storeName, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.userId = userId;
        this.userName = userName;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
