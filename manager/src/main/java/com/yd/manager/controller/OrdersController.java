package com.yd.manager.controller;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrdersController extends CommonController {

    @GetMapping
    public Result<OrdersDTO> listCollect(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        return Result.success(ordersRepository.getOrdersDTO(DateRange.of(begin, end).toTimeRange(), null));
    }

}
