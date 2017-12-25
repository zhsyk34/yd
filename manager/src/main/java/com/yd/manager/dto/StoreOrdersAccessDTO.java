package com.yd.manager.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor(staticName = "of")
@Getter
public class StoreOrdersAccessDTO {
    @JsonUnwrapped
    private final StoreOrdersDTO storeOrdersDTO;
    @JsonUnwrapped
    private final AccessRecordDTO accessRecordDTO;

    private static final AccessRecordDTO EMPTY = AccessRecordDTO.from(0, 0);

    public static List<StoreOrdersAccessDTO> fromMerge(@NonNull List<StoreOrdersDTO> stores, List<StoreAccessRecordDTO> records) {
        if (CollectionUtils.isEmpty(records)) {
            return stores.stream().map(dto -> StoreOrdersAccessDTO.of(dto, EMPTY)).collect(toList());
        }

        Map<Long, AccessRecordDTO> map = records.stream().collect(toMap(StoreAccessRecordDTO::getStoreId, identity()));
        return stores.stream().map(dto -> StoreOrdersAccessDTO.of(dto, Optional.ofNullable(map.get(dto.getStoreId())).orElse(EMPTY))).collect(toList());
    }
}
