package com.yd.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    /*user*/
    private String name;
    private String phone;
    private String address;
    private BigDecimal balance;
    private LocalDateTime createTime;

    /*access*/
    private int count;

    /*orders*/
    private long payCount;
    private BigDecimal paySum;
    private Double payAvg;

}
