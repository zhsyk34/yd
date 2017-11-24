package com.yd.manager.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchandiseDTO2 {
    //商品
    private long id;
    private String name;
    private String code;

    //种类
    private String category;

    //店铺
    private Long storeId;
    private String storeName;

    //规格
    private String specNo;
    private String specName;
    private BigDecimal price;

    //sum():销量
    private Long sales;
}
