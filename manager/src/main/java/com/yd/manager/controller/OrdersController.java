package com.yd.manager.controller;

import com.yd.manager.dto.OrdersDTO;
import com.yd.manager.dto.OrdersDateDTO;
import com.yd.manager.dto.util.Result;
import com.yd.manager.interceptor.OwnerStore;
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
public class OrdersController extends CommonController {

    @GetMapping
    public Result<OrdersDTO> getBetween(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end, @OwnerStore List<Long> stores) {
        return Result.success(ordersService.getBetween(begin, end, stores));
    }

    @GetMapping("range/week")
    public Result<List<OrdersDateDTO>> listForWeek(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.listForWeek(stores));
    }

    @GetMapping("all")
    public Result<OrdersDTO> getForAll(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.getForAll(stores));
    }

    @GetMapping("today")
    public Result<OrdersDTO> getForToday(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.getForToday(stores));
    }

    @GetMapping("week")
    public Result<OrdersDTO> getForWeek(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.getForWeek(stores));
    }

    @GetMapping("month")
    public Result<OrdersDTO> getForMonth(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.getForMonth(stores));
    }

    @GetMapping("season")
    public Result<OrdersDTO> getForSeason(@OwnerStore List<Long> stores) {
        return Result.success(ordersService.getForSeason(stores));
    }

}
