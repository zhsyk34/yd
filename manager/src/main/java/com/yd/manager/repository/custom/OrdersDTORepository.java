package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserOrdersDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersDTORepository {

    List<UserOrdersDTO> findUserOrderDTO(Long userId, LocalDateTime begin, LocalDateTime end, List<Long> stores, Pageable pageable);
}
