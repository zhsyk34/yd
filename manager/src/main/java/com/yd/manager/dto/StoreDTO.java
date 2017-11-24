package com.yd.manager.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreDTO {
    /*store*/
    private String name;
    private String code;
    private String address;

    /*access*/
    private int accessCount;

    /*orders*/
    private int orderCount;
    private BigDecimal orderSum;
    private BigDecimal orderAvg;

    //TODO
    /*manager*/
    private BigDecimal managerBalance;
}
