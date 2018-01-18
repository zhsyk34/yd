package com.yd.manager.dto.orders;

import com.yd.manager.dto.device.StoreDevice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Getter
public class StoreOrdersDeviceDTO extends StoreOrdersDTO {
    @Setter
    private Integer state;

    private StoreOrdersDeviceDTO(long storeId, String storeName, String storeAddress, long ordersCount, BigDecimal ordersMoney, BigDecimal ordersProfit, Double ordersAverage) {
        super(storeId, storeName, storeAddress, ordersCount, ordersMoney, ordersProfit, ordersAverage);
    }

    private StoreOrdersDeviceDTO(StoreOrdersDTO dto) {
        this(dto.getStoreId(), dto.getStoreName(), dto.getStoreAddress(), dto.getOrdersCount(), dto.getOrdersMoney(), dto.getOrdersProfit(), dto.getOrdersAverage());
    }

    public static StoreOrdersDeviceDTO from(StoreOrdersDTO storeOrdersDTO, List<StoreDevice> devices) {
        StoreOrdersDeviceDTO dto = new StoreOrdersDeviceDTO(storeOrdersDTO);
        Optional.ofNullable(devices)
                .map(Collection::stream)
                .map(s -> s.collect(toMap(StoreDevice::getStoreName, StoreDevice::getState)))
                .map(map -> map.get(dto.getStoreName()))
                .ifPresent(dto::setState);
        return dto;
    }
}
