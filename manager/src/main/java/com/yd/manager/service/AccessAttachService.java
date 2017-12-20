package com.yd.manager.service;

import com.yd.manager.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessAttachService {
    private final UserService userService;
    private final StoreService storeService;
    private final AccessRecordService accessRecordService;

    public Page<UserOrdersAccessDTO> pageUserOrdersAccessDTO(String nameOrPhone, List<Long> stores, Pageable pageable) {
        Page<UserOrdersDTO> page = userService.pageUserOrdersDTO(nameOrPhone, null, null, stores, pageable);

        List<UserOrdersDTO> list = page.getContent();
        if (CollectionUtils.isEmpty(list)) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }

        List<Long> users = list.stream().map(UserOrdersDTO::getUserId).collect(toList());

        List<UserAccessRecordDTO> records = accessRecordService.listUserAccessRecordDTO(users, null, stores);
        List<UserOrdersAccessDTO> result = UserOrdersAccessDTO.fromMerge(list, records);
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    public Page<StoreOrdersAccessDTO> pageStoreOrdersAccessDTO(String nameOrCode, List<Long> stores, Pageable pageable) {
        Page<StoreOrdersDTO> page = storeService.pageStoreOrdersDTO(nameOrCode, stores, pageable);

        List<StoreOrdersDTO> list = page.getContent();
        if (CollectionUtils.isEmpty(list)) {
            return new PageImpl<>(Collections.emptyList(), pageable, page.getTotalElements());
        }

        List<Long> storeIds = list.stream().map(StoreOrdersDTO::getStoreId).collect(toList());

        List<StoreAccessRecordDTO> records = accessRecordService.listStoreAccessRecordDTO(null, storeIds);
        List<StoreOrdersAccessDTO> result = StoreOrdersAccessDTO.fromMerge(list, records);
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }
}
