package com.yd.manager.dto;

import com.yd.manager.util.TimeUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * (管理员)所有店铺按日统计订单
 */
@Data
public class OrdersDateDTO {
    private final String date;

    private final long ordersCount;
    private final BigDecimal ordersMoney;
    private final Double ordersAverage;

    public static OrdersDateDTO of(LocalDate localDate, OrdersDTO dto) {
        String date = TimeUtils.format(localDate);
        return dto == null ? new OrdersDateDTO(date, 0, null, null) : new OrdersDateDTO(date, dto.getOrdersCount(), dto.getOrdersMoney(), dto.getOrdersAverage());
    }
}
