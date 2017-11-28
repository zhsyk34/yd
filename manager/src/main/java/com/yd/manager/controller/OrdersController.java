package com.yd.manager.controller;

import com.yd.manager.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrdersController extends CommonController {

    @GetMapping("detail")
    public Result<List<OrdersDTO>> listDetail() {
        LocalDate day = LocalDate.of(2017, 11, 13);
        LocalDateTime begin = LocalDateTime.of(day, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(day, LocalTime.MAX);
        Pageable pageable = new PageRequest(0, 5);
        return Result.success(ordersRepository.findOrdersCollectDTO(begin, end, null, pageable));
    }

    @GetMapping
    public Result<Orders2DTO> listCollect() {
        return Result.success(ordersRepository.findOrdersCollectDTO2(null, null, null));
    }

    @GetMapping("week")
    public Result<List<Orders2DTO>> listCollectForWeek() {
        List<Orders2DTO> result = new ArrayList<>();
        LocalDate today = LocalDate.of(2017, 11, 18);//TODO
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            LocalDateTime begin = LocalDateTime.of(day, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(day, LocalTime.MAX);

            result.add(ordersRepository.findOrdersCollectDTO2(begin, end, null));
        }
        return Result.success(result);
    }
}
