package com.yd.manager.controller;

import com.yd.manager.dto.StoreOrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("stores")
public class StoreController extends CommonController {

    @GetMapping
    public Result<Page<StoreOrdersDTO>> list(String nameOrCode, Pageable pageable) {
        return Result.success(storeRepository.pageStoreOrdersDTO(nameOrCode, null, null, pageable));
    }

    @GetMapping("top5")
    public Result<List<StoreOrdersDTO>> listTop5(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        return Result.success(storeRepository.listStoreOrdersDTO(null, DateRange.of(begin, end).toTimeRange(), null, new PageRequest(0, 5)));
    }
}
