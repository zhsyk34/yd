package com.yd.manager.controller;

import com.yd.manager.dto.AccessRecordDateDTO;
import com.yd.manager.dto.util.DateRange;
import com.yd.manager.dto.util.Result;
import com.yd.manager.interceptor.OwnerStore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

//TODO:stores==>@RequestParam(required = false)
@RestController
@RequestMapping("records")
public class AccessRecordController extends CommonController {

    @GetMapping("range")
    public Result<List<AccessRecordDateDTO>> listBetweenByStores(@RequestParam @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam @DateTimeFormat(iso = DATE) LocalDate end, @OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listBetweenByStores(stores, DateRange.of(begin, end)));
    }

    @GetMapping("range/recent")
    public Result<List<AccessRecordDateDTO>> listForRecentByStores(@OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForRecentByStores(stores));
    }

    @GetMapping("range/week")
    public Result<List<AccessRecordDateDTO>> listForWeekByStores(@OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForWeekByStores(stores));
    }

    @GetMapping("range/month")
    public Result<List<AccessRecordDateDTO>> listForMonthByStores(@OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForMonthByStores(stores));
    }

    @GetMapping("range/season")
    public Result<List<AccessRecordDateDTO>> listForSeasonByStores(@OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForSeasonByStores(stores));
    }

    /*用户*/
    @GetMapping("users/{userId}/range")
    public Result<List<AccessRecordDateDTO>> listBetweenByUser(@PathVariable long userId, @RequestParam @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam @DateTimeFormat(iso = DATE) LocalDate end, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listBetweenByUser(userId, DateRange.of(begin, end), stores));
    }

    @GetMapping("users/{userId}/range/week")
    public Result<List<AccessRecordDateDTO>> listForWeekByUser(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForWeekByUser(userId, stores));
    }

    @GetMapping("users/{userId}/range/month")
    public Result<List<AccessRecordDateDTO>> listForMonthByUser(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForMonthByUser(userId, stores));
    }

    @GetMapping("users/{userId}/range/season")
    public Result<List<AccessRecordDateDTO>> listForSeasonByUser(@PathVariable long userId, @RequestParam(required = false) @OwnerStore List<Long> stores) {
        return Result.success(accessRecordService.listForSeasonByUser(userId, stores));
    }

    /*店铺*/
    @GetMapping("stores/{storeId}/range")
    public Result<List<AccessRecordDateDTO>> listBetweenByStore(@PathVariable long storeId, @RequestParam @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(accessRecordService.listBetweenByStore(storeId, DateRange.of(begin, end)));
    }

    @GetMapping("stores/{storeId}/range/week")
    public Result<List<AccessRecordDateDTO>> listForWeekByStore(@PathVariable long storeId) {
        return Result.success(accessRecordService.listForWeekByStore(storeId));
    }

    @GetMapping("stores/{storeId}/range/month")
    public Result<List<AccessRecordDateDTO>> listForMonthByStore(@PathVariable long storeId) {
        return Result.success(accessRecordService.listForMonthByStore(storeId));
    }

    @GetMapping("stores/{storeId}/range/season")
    public Result<List<AccessRecordDateDTO>> listForSeasonByStore(@PathVariable long storeId) {
        return Result.success(accessRecordService.listForSeasonByStore(storeId));
    }

}
