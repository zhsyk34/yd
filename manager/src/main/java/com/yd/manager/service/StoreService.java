package com.yd.manager.service;

import com.yd.manager.dto.device.StoreDevice;
import com.yd.manager.dto.orders.StoreOrdersDTO;
import com.yd.manager.dto.orders.StoreOrdersDateDTO;
import com.yd.manager.dto.orders.StoreOrdersDeviceDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.repository.StoreRepository;
import com.yd.manager.util.TimeUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yd.manager.service.DeviceService.SUPER_ACCOUNT;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final DeviceService deviceService;

    public Page<StoreOrdersDeviceDTO> pageStoreOrdersDeviceDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        List<StoreDevice> devices = deviceService.listStoreDevice(SUPER_ACCOUNT);
        Page<StoreOrdersDTO> page = storeRepository.pageStoreOrdersDTO(nameOrCode, null, stores, pageable);
        List<StoreOrdersDTO> ds = page.getContent();
        List<StoreOrdersDeviceDTO> list = ds.stream().map(dto -> StoreOrdersDeviceDTO.from(dto, devices)).collect(toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    public Page<StoreOrdersDTO> pageStoreOrdersDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        return storeRepository.pageStoreOrdersDTO(nameOrCode, null, stores, pageable);
    }

    public List<StoreOrdersDTO> listTop5(LocalDate begin, LocalDate end, List<Long> stores) {
        return storeRepository.listStoreOrdersDTO(null, DateRange.of(begin, end).toTimeRange(), stores, new PageRequest(0, 5));
    }

    public StoreOrdersDTO getAll(long storeId) {
        return storeRepository.getStoreOrdersDTO(storeId, null);
    }

    public List<StoreOrdersDateDTO> listBetween(long storeId, @NonNull LocalDate begin, @NonNull LocalDate end) {
        List<StoreOrdersDateDTO> list = new ArrayList<>();

        while (!begin.isAfter(end)) {
            StoreOrdersDateDTO dto = storeRepository.getStoreOrdersDateDTO(storeId, begin);
            list.add(Optional.ofNullable(dto).orElse(StoreOrdersDateDTO.of(TimeUtils.format(begin), storeId)));
            begin = begin.plusDays(1);
        }

        return list;
    }

    public StoreOrdersDateDTO getForToday(long storeId) {
        return storeRepository.getStoreOrdersDateDTO(storeId, LocalDate.now());
    }

    public List<StoreOrdersDateDTO> listForRecent(long storeId) {
        return this.listBetween(storeId, DateRange.recent());
    }

    public List<StoreOrdersDateDTO> listForWeek(long storeId) {
        return this.listBetween(storeId, DateRange.week());
    }

    public List<StoreOrdersDateDTO> listForMonth(long storeId) {
        return this.listBetween(storeId, DateRange.month());
    }

    public List<StoreOrdersDateDTO> listForSeason(long storeId) {
        return this.listBetween(storeId, DateRange.season());
    }

    private List<StoreOrdersDateDTO> listBetween(long storeId, DateRange dateRange) {
        LocalDate begin = dateRange.getBegin();
        LocalDate end = dateRange.getEnd();

        return begin != null && end != null ? this.listBetween(storeId, begin, end) : null;
    }

}
