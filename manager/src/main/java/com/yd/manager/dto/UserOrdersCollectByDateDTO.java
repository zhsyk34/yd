package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户订单按日汇总
 */
@Data
public class UserOrdersCollectByDateDTO {
    private final long id;
    private final String name;

    private final String day;

    /*orders*/
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
