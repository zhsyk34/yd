package com.yd.manager.controller;

import com.yd.manager.dto.StoreOrdersAccessDTO;
import com.yd.manager.dto.UserOrdersAccessDTO;
import com.yd.manager.interceptor.OwnerStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO:stores==>@RequestParam(required = false)
@RestController
@RequestMapping("attach")
public class AccessAttachController extends CommonController {

    @GetMapping("users")
    public Page<UserOrdersAccessDTO> pageUserOrdersAccessDTO(String nameOrPhone, Pageable pageable, @OwnerStore List<Long> stores) {
        return accessAttachService.pageUserOrdersAccessDTO(nameOrPhone, stores, pageable);
    }

    @GetMapping("stores")
    public Page<StoreOrdersAccessDTO> pageStoreOrdersAccessDTO(String nameOrCode, Pageable pageable, @OwnerStore List<Long> stores) {
        return accessAttachService.pageStoreOrdersAccessDTO(nameOrCode, stores, pageable);
    }
}
