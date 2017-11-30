package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO:用户在店铺访问/消费 列表
 */
@Data
public class UserStoreOrdersDTO {
    private final long id;
    private final String name;

    /*store*/
    private final long storeId;
    private final String storeName;

    /*orders*/
    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;
}
