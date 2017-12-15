package com.yd.manager.controller;

import com.yd.manager.dto.UserOrdersDTO;
import com.yd.manager.dto.UserOrdersDateDTO;
import com.yd.manager.dto.util.Result;
import com.yd.manager.interceptor.OwnerStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("users")
public class UserController extends CommonController {

    @GetMapping
    public Result<Page<UserOrdersDTO>> list(String nameOrPhone, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end, Pageable pageable, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.pageUserOrdersDTO(nameOrPhone, begin, end, stores, pageable));
    }

    @GetMapping("{userId}/range")
    public Result<List<UserOrdersDateDTO>> listBetween(@PathVariable long userId, @RequestParam @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam @DateTimeFormat(iso = DATE) LocalDate end, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.listBetween(userId, begin, end, stores));
    }

    @GetMapping("{userId}/range/today")
    public Result<UserOrdersDateDTO> getForToday(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.getForToday(userId, stores));
    }

    @GetMapping("{userId}/range/recent")
    public Result<List<UserOrdersDateDTO>> listForRecent(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.listForRecent(userId, stores));
    }

    @GetMapping("{userId}/range/week")
    public Result<List<UserOrdersDateDTO>> listForWeek(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.listForWeek(userId, stores));
    }

    @GetMapping("{userId}/range/month")
    public Result<List<UserOrdersDateDTO>> listForMonth(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.listForMonth(userId, stores));
    }

    @GetMapping("{userId}/range/season")
    public Result<List<UserOrdersDateDTO>> listForSeason(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.listForSeason(userId, stores));
    }

    @GetMapping("register/count/range")
    public Result<Long> countRange(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.countRange(begin, end, stores));
    }

    @GetMapping("register/count/toady")
    public Result<Long> countToday(@RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.countToday(stores));
    }

    @GetMapping("register/count/recent")
    public Result<List<Long>> countRecent(@RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(userService.countRecent(stores));
    }
}
