package com.yd.manager.controller;

import com.yd.manager.dto.StoreOrdersDTO;
import com.yd.manager.dto.StoreOrdersDateDTO;
import com.yd.manager.dto.util.Result;
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
    public Result<Page<StoreOrdersDTO>> list(String nameOrCode, Pageable pageable) {
        return Result.success(storeService.pageStoreOrdersDTO(nameOrCode, null, pageable));
    }

    @GetMapping("top5")
    public Result<List<StoreOrdersDTO>> listTop5(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(storeService.listTop5(begin, end, null));
    }

    @GetMapping("{storeId}/all")
    public Result<StoreOrdersDTO> getUntilNow(@PathVariable long storeId) {
        return Result.success(storeService.getUntilNow(storeId));
    }

    @GetMapping("{storeId}/range")
    public Result<List<StoreOrdersDateDTO>> listRange(@PathVariable long storeId, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin, @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end) {
        return Result.success(storeService.listForDateRange(storeId, begin, end));
    }

    @GetMapping("{storeId}/today")
    public Result<StoreOrdersDateDTO> listToday(@PathVariable long storeId) {
        return Result.success(storeService.getForToday(storeId));
    }

    @GetMapping("{storeId}/week")
    public Result<List<StoreOrdersDateDTO>> listForWeek(@PathVariable long storeId) {
        return Result.success(storeService.listForWeek(storeId));
    }

    @GetMapping("{storeId}/month")
    public Result<List<StoreOrdersDateDTO>> listForMonth(@PathVariable long storeId) {
        return Result.success(storeService.listForMonth(storeId));
    }

    @GetMapping("{storeId}/season")
    public Result<List<StoreOrdersDateDTO>> listForSeason(@PathVariable long storeId) {
        return Result.success(storeService.listForSeason(storeId));
    }
}
