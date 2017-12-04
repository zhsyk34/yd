package com.yd.manager.controller;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.Result;
import com.yd.manager.dto.util.DateRange;
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
    public Result<List<OrdersDTO>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end,
            Pageable pageable
    ) {
        return Result.success(ordersRepository.listOrdersDTO(DateRange.of(begin, end).toTimeRange(), null, pageable));
    }
}
