package com.yd.manager.controller;

import com.yd.manager.dto.StoreOrdersDTO;
import com.yd.manager.dto.StoreOrdersDateDTO;
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
@RequestMapping("stores")
public class StoreController extends CommonController {

    @GetMapping
    public Result<Page<StoreOrdersDTO>> list(String nameOrCode, Pageable pageable, @OwnerStore List<Long> stores) {
        return Result.success(storeService.pageStoreOrdersDTO(nameOrCode, stores, pageable));
    }

    @GetMapping("top5")
    public Result<List<StoreOrdersDTO>> listTop5(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end, @OwnerStore List<Long> stores) {
        return Result.success(storeService.listTop5(begin, end, stores));
    }

    @GetMapping("{storeId}")
    public Result<StoreOrdersDTO> getAll(@PathVariable long storeId) {
        return Result.success(storeService.getAll(storeId));
    }

    @GetMapping("{storeId}/range")
    public Result<List<StoreOrdersDateDTO>> listBetween(@PathVariable long storeId, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(storeService.listBetween(storeId, begin, end));
    }

    @GetMapping("{storeId}/range/today")
    public Result<StoreOrdersDateDTO> getForToday(@PathVariable long storeId) {
        return Result.success(storeService.getForToday(storeId));
    }

    @GetMapping("{storeId}/range/recent")
    public Result<List<StoreOrdersDateDTO>> listForRecent(@PathVariable long storeId) {
        return Result.success(storeService.listForRecent(storeId));
    }

    @GetMapping("{storeId}/range/week")
    public Result<List<StoreOrdersDateDTO>> listForWeek(@PathVariable long storeId) {
        return Result.success(storeService.listForWeek(storeId));
    }

    @GetMapping("{storeId}/range/month")
    public Result<List<StoreOrdersDateDTO>> listForMonth(@PathVariable long storeId) {
        return Result.success(storeService.listForMonth(storeId));
    }

    @GetMapping("{storeId}/range/season")
    public Result<List<StoreOrdersDateDTO>> listForSeason(@PathVariable long storeId) {
        return Result.success(storeService.listForSeason(storeId));
    }
}
