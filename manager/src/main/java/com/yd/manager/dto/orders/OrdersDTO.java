package com.yd.manager.dto.orders;

import lombok.Data;

import java.math.BigDecimal;

/**
 * (管理员)所有店铺统计订单
 */
@Data
public class OrdersDTO {
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    static final OrdersDTO EMPTY = new OrdersDTO(0, null, null, null);
    private final Double ordersAverage;
    private final BigDecimal ordersProfit;
}
