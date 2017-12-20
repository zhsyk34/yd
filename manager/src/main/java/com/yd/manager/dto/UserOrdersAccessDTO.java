package com.yd.manager.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

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
            return userOrdersDTOS.stream().map(dto -> UserOrdersAccessDTO.of(dto, null)).collect(toList());
        }

        Map<Long, UserAccessRecordDTO> map = userAccessRecordDTOS.stream().collect(toMap(UserAccessRecordDTO::getUserId, identity()));
        return userOrdersDTOS.stream().map(dto -> UserOrdersAccessDTO.of(dto, map.get(dto.getUserId()))).collect(toList());
    }
}
