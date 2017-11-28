package com.yd.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户订单明细
 */
@Data
@AllArgsConstructor
public class UserOrdersDTO {
    private long userId;
    private String userName;

    private long storeId;
    private String storeName;

    private long orderId;
    private BigDecimal actual;
}
