package com.yd.manager.dto.record;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.yd.manager.dto.orders.UserOrdersDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.yd.manager.dto.record.AccessRecordDTO.EMPTY;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserOrdersAccessDTO {
    @JsonUnwrapped
    private final UserOrdersDTO userOrdersDTO;
    @JsonUnwrapped
    private final AccessRecordDTO accessRecordDTO;

    public static List<UserOrdersAccessDTO> fromMerge(@NonNull List<UserOrdersDTO> userOrdersDTOS, List<UserAccessRecordDTO> userAccessRecordDTOS) {
        if (CollectionUtils.isEmpty(userAccessRecordDTOS)) {
            return userOrdersDTOS.stream().map(dto -> UserOrdersAccessDTO.of(dto, EMPTY)).collect(toList());
        }

        Map<Long, AccessRecordDTO> map = userAccessRecordDTOS.stream().collect(toMap(UserAccessRecordDTO::getUserId, identity()));
        return userOrdersDTOS.stream().map(dto -> UserOrdersAccessDTO.of(dto, Optional.ofNullable(map.get(dto.getUserId())).orElse(EMPTY))).collect(toList());
    }
}
