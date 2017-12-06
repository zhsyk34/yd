package com.yd.manager.controller;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.util.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("orders")
public class OrdersController extends CommonController {

    @GetMapping("range")
    public Result<OrdersDTO> getForDateRange(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(ordersService.getForDateRange(begin, end, null));
    }

    @GetMapping("all")
    public Result<OrdersDTO> getUntilNow() {
        return Result.success(ordersService.getUntilNow(null));
    }

    @GetMapping("today")
    public Result<OrdersDTO> getForToday() {
        return Result.success(ordersService.getForToday(null));
    }

    @GetMapping("week")
    public Result<OrdersDTO> getForWeek() {
        return Result.success(ordersService.getForWeek(null));
    }

    @GetMapping("month")
    public Result<OrdersDTO> getForMonth() {
        return Result.success(ordersService.getForMonth(null));
    }

    @GetMapping("season")
    public Result<OrdersDTO> getForSeason() {
        return Result.success(ordersService.getForSeason(null));
    }

}
