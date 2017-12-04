package com.yd.manager.repository.custom;

import com.yd.manager.dto.UserOrdersCollectByDateDTO;
import com.yd.manager.dto.UserOrdersCollectDTO;
import com.yd.manager.dto.UserStoreOrdersDTO;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserDTORepository {

    List<UserOrdersCollectDTO> listUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    long countUserOrderCollectDTO(String nameOrPhone, List<Long> stores);

    Page<UserOrdersCollectDTO> pageUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores);

    UserOrdersCollectByDateDTO getUserOrderCollectByDateDTO(long userId, @NonNull LocalDate day, List<Long> stores);

    long countByCreateTime(TimeRange timeRange, List<Long> stores);

}
