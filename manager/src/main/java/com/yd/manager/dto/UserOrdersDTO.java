package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户订单统计
 */
@Data
public class UserOrdersDTO {
    private final long userId;
    private final String userName;
    private final String userPhone;
    private final String userAddress;
    private final BigDecimal userBalance;
    private final LocalDateTime userCreateTime;

    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
