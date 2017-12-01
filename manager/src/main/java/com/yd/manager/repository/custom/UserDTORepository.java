package com.yd.manager.repository.custom;

import com.yd.manager.dto.*;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserDTORepository {

    List<UserOrderCollectDTO> listUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    long countUserOrderCollectDTO(String nameOrPhone, List<Long> stores);

    Page<UserOrderCollectDTO> pageUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    List<UserStoreOrdersDTO> listUserStoreOrdersDTO(long userId, TimeRange timeRange, List<Long> stores);

    UserOrderCollectByDateDTO getUserOrderCollectByDateDTO(long userId, @NonNull LocalDate day, List<Long> stores);

}
