package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 管理员所有店铺订单汇总
 */
@Data
public class OrdersCollect2DTO {
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
