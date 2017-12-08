package com.yd.manager.controller;

import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.dto.util.Result;
import com.yd.manager.interceptor.OwnerStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("merchandises")
public class MerchandiseController extends CommonController {

    @GetMapping
    public Result<Page<MerchandiseOrdersDTO>> retrieve(String nameOrCode, Pageable pageable, @OwnerStore List<Long> stores) {
        return Result.success(merchandiseService.pageMerchandiseOrdersDTO(nameOrCode, stores, pageable));
    }
}
