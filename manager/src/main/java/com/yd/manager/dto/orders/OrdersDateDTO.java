package com.yd.manager.dto.orders;

import com.yd.manager.util.TimeUtils;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * (管理员)所有店铺按日统计订单
 */
@Getter
public class OrdersDateDTO extends OrdersDTO {
    private final String date;

    private OrdersDateDTO(String date, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(ordersCount, ordersMoney, ordersProfit, ordersAverage);
        this.date = date;
    }

    private static OrdersDateDTO from(@NonNull LocalDate localDate, @NonNull OrdersDTO dto) {
        return new OrdersDateDTO(TimeUtils.format(localDate), dto.getOrdersCount(), dto.getOrdersMoney(), dto.getOrdersProfit(), dto.getOrdersAverage());
    }

    public static OrdersDateDTO of(@NonNull LocalDate localDate, OrdersDTO dto) {
        return from(localDate, Optional.ofNullable(dto).orElse(EMPTY));
    }
}
