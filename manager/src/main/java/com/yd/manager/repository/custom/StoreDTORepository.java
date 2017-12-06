package com.yd.manager.repository.custom;

import com.yd.manager.dto.StoreOrdersDTO;
import com.yd.manager.dto.StoreOrdersDateDTO;
import com.yd.manager.dto.util.TimeRange;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StoreDTORepository {

    List<StoreOrdersDTO> listStoreOrdersDTO(String nameOrCode, TimeRange timeRange, List<Long> stores, Pageable pageable);

    List<StoreOrdersDTO> listStoreOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable);

    long countStoreOrdersDTO(String nameOrCode, List<Long> stores);

    Page<StoreOrdersDTO> pageStoreOrdersDTO(String nameOrCode, TimeRange timeRange, List<Long> stores, Pageable pageable);

    StoreOrdersDTO getStoreOrdersDTO(long storeId, TimeRange timeRange);

    StoreOrdersDateDTO getStoreOrdersDateDTO(long storeId, @NonNull LocalDate date);

}
