package com.yd.manager.controller;

import com.yd.manager.dto.UserOrdersDTO;
import com.yd.manager.dto.UserOrdersDateDTO;
import com.yd.manager.dto.util.Result;
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

    private List<Long> stores = null;//AuthInitializationListener.getStores("");TODO

    @GetMapping
    public Result<Page<UserOrdersDTO>> list(String nameOrPhone, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end, Pageable pageable) {
        return Result.success(userService.list(nameOrPhone, begin, end, null, pageable));
    }

    @GetMapping("{userId}/range")
    public Result<List<UserOrdersDateDTO>> listByDate(@PathVariable long userId, @RequestParam @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(userService.listForDateRange(userId, begin, end, stores));
    }

    @GetMapping("{userId}/today")
    public Result<UserOrdersDateDTO> listToday(@PathVariable long userId) {
        return Result.success(userService.getForToday(userId, stores));
    }

    @GetMapping("{userId}/week")
    public Result<List<UserOrdersDateDTO>> listWeek(@PathVariable long userId) {
        return Result.success(userService.listForWeek(userId, stores));
    }

    @GetMapping("{userId}/month")
    public Result<List<UserOrdersDateDTO>> listMonth(@PathVariable long userId) {
        return Result.success(userService.listForMonth(userId, stores));
    }

    @GetMapping("{userId}/season")
    public Result<List<UserOrdersDateDTO>> listSeason(@PathVariable long userId) {
        return Result.success(userService.listForSeason(userId, stores));
    }

    @GetMapping("register/count/range")
    public Result<Long> countRange(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(userService.countRange(begin, end, stores));
    }

    @GetMapping("register/count/toady")
    public Result<Long> countToday() {
        return Result.success(userService.countToday(stores));
    }
}
