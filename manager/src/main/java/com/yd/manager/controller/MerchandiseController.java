package com.yd.manager.controller;

import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.dto.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("merchandises")
public class MerchandiseController extends CommonController {

    @GetMapping
    public Result<Page<MerchandiseOrdersDTO>> retrieve(String nameOrCode, Pageable pageable) {
        return Result.success(merchandiseService.pageMerchandiseOrdersDTO(nameOrCode, null, pageable));
    }
}
