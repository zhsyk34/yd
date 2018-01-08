package com.yd.manager.dto.orders;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 用户订单按日统计
 */
@Getter
public class UserOrdersDateDTO extends OrdersDTO {
    private final String date;
    private final long userId;

    public UserOrdersDateDTO(String date, long userId, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.date = date;
        this.userId = userId;
    }

    public static UserOrdersDateDTO of(String date, long userId) {
        return new UserOrdersDateDTO(date, userId, 0, null, null, null);
    }
}
