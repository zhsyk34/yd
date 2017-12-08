package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户订单按日统计
 */
@Data
public class UserOrdersDateDTO {
    private final long userId;
    private final String userName;

    private final String date;

    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
