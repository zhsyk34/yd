package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息及销量
 */
@Data
public class MerchandiseOrdersDTO {
    //商品
    private final long id;
    private final String name;
    private final String code;

    //种类
    private final String category;

    //店铺
    private final long storeId;
    private final String storeName;

    //规格
    private final String specCode;
    private final String specName;
    private final BigDecimal price;

    //sum():销量
    private final Long sales;
}
