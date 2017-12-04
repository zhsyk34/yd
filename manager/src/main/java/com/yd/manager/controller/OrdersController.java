package com.yd.manager.controller;

import com.yd.manager.dto.OrdersCollectDTO;
import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.Result;
import com.yd.manager.dto.util.DateRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrdersController extends CommonController {

    @GetMapping
    public Result<OrdersCollectDTO> listCollect(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        return Result.success(ordersRepository.getOrdersCollectDTO(DateRange.of(begin, end).toTimeRange(), null));
    }

    @GetMapping("top5")
    public Result<List<OrdersDTO>> listTop5(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        return Result.success(ordersRepository.listOrdersDTO(DateRange.of(begin, end).toTimeRange(), null, new PageRequest(0, 5)));
    }

}
