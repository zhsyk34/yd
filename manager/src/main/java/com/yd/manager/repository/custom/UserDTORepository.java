package com.yd.manager.repository.custom;

import com.yd.manager.dto.TimeRange;
import com.yd.manager.dto.UserOrderCollectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDTORepository {

    List<UserOrderCollectDTO> listUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

    long countUserOrderCollectDTO(String nameOrPhone, List<Long> stores);

    Page<UserOrderCollectDTO> pageUserOrderCollectDTO(String nameOrPhone, TimeRange timeRange, List<Long> stores, Pageable pageable);

}
