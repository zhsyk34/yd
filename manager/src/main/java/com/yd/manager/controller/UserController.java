package com.yd.manager.controller;

import com.yd.manager.dto.Result;
import com.yd.manager.dto.UserOrdersCollectByDateDTO;
import com.yd.manager.dto.UserOrdersCollectDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.TimeRange;
import com.yd.manager.listener.AuthInitializationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController extends CommonController {

    @GetMapping
    public Result<Page<UserOrdersCollectDTO>> listCollect(
            String nameOrPhone,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end,
            Pageable pageable
    ) {
        logger.info("nameOrPhone:{}", nameOrPhone);
        logger.info("begin:{}", begin);
        logger.info("end:{}", end);
        logger.info("pageable:{}", pageable);
        List<Long> stores = AuthInitializationListener.getStores("");
        TimeRange timeRange = DateRange.of(begin, end).toTimeRange();
        Page<UserOrdersCollectDTO> page = userRepository.pageUserOrderCollectDTO(nameOrPhone, timeRange, stores, pageable);
        return Result.success(page);
    }

    @GetMapping("{userId}/today")
    public Result<UserOrdersCollectByDateDTO> listToday(@PathVariable long userId) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userService.getForToday(userId, stores));
    }

    @GetMapping("{userId}/week")
    public Result<List<UserOrdersCollectByDateDTO>> listWeek(@PathVariable long userId) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userService.listForWeek(userId, stores));
    }

    @GetMapping("{userId}/month")
    public Result<List<UserOrdersCollectByDateDTO>> listMonth(@PathVariable long userId) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userService.listForMonth(userId, stores));
    }

    @GetMapping("{userId}/season")
    public Result<List<UserOrdersCollectByDateDTO>> listSeason(@PathVariable long userId) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userService.listForSeason(userId, stores));
    }

    @GetMapping("{userId}")
    public Result<List<UserOrdersCollectByDateDTO>> listByDate(
            @PathVariable long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userService.listForDateRange(userId, begin, end, stores));
    }

    @GetMapping("register/count")
    public Result<Long> countByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userRepository.countByCreateTime(DateRange.of(begin, end).toTimeRange(), stores));
    }

    @GetMapping("register/count/toady")
    public Result<Long> countToday() {
        List<Long> stores = AuthInitializationListener.getStores("");
        return Result.success(userRepository.countByCreateTime(DateRange.today().toTimeRange(), stores));
    }
}
