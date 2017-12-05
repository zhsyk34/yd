package com.yd.manager.controller;

import com.yd.manager.dto.MerchandiseOrdersDTO;
import com.yd.manager.dto.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("merchandises")
@Slf4j
public class MerchandiseController extends CommonController {

    @GetMapping
    public Result<Page<MerchandiseOrdersDTO>> retrieve(String nameOrCode, Pageable pageable) {
        logger.debug("nameOrCode:{}, pageable:{}", nameOrCode, pageable);
        return Result.success(merchandiseRepository.pageMerchandiseOrdersDTO(nameOrCode, null, pageable));
    }
}
