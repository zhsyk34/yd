package com.yd.manager.dto.orders;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户订单统计
 */
@Getter
public class UserOrdersDTO extends OrdersDTO {
    private final long userId;
    private final String userName;
    private final String userPhone;
    private final String userAddress;
    private final BigDecimal userBalance;
    private final LocalDateTime userCreateTime;

    public UserOrdersDTO(long userId, String userName, String userPhone, String userAddress, BigDecimal userBalance, LocalDateTime userCreateTime, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userBalance = userBalance;
        this.userCreateTime = userCreateTime;
    }
}
