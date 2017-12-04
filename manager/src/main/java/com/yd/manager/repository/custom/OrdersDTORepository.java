package com.yd.manager.repository.custom;

import com.yd.manager.dto.OrdersCollectDTO;
import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.TimeRange;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersDTORepository {

    List<OrdersDTO> listOrdersDTO(TimeRange timeRange, List<Long> stores, Pageable pageable);

    OrdersCollectDTO getOrdersCollectDTO(TimeRange timeRange, List<Long> stores);
}
