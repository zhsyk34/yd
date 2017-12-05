package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserOrdersDTO;
import com.yd.manager.dto.UserOrdersDateDTO;
import com.yd.manager.dto.UserStoreOrdersDTO;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserDTORepository {

    List<UserOrdersDTO> listUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    List<UserOrdersDTO> listUserOrdersDTO(String nameOrPhone, List<Long> stores, Pageable pageable);

    long countUserOrdersDTO(String nameOrPhone, List<Long> stores);

    Page<UserOrdersDTO> pageUserOrdersDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores);

    UserOrdersDateDTO getUserOrdersDateDTO(long userId, @NonNull LocalDate date, List<Long> stores);

    long countByCreateTime(TimeRange timeRange, List<Long> stores);

}
