package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * (管理员)所有店铺统计订单
 */
@Data
public class OrdersDTO {
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
