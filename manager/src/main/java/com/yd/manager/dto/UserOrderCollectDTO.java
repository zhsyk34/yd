package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户信息及订单汇总
 */
@Data
public class UserOrderCollectDTO {
    private final long id;
    private final String name;
    private final String phone;
    private final String address;
    private final BigDecimal balance;
    private final LocalDateTime createTime;

    /*orders*/
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
