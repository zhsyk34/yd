package com.yd.manager.repository.custom;

import com.yd.manager.dto.orders.OrdersDTO;
import com.yd.manager.dto.util.TimeRange;

import java.util.List;

public interface OrdersDTORepository {

    OrdersDTO getOrdersDTO(TimeRange timeRange, List<Long> stores);
}
