package com.yd.manager.controller;

import com.yd.manager.dto.*;
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

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//    }

//    @GetMapping
//    public Result<Boolean> login(HttpServletResponse response, @RequestHeader(required = false, defaultValue = "") String manager) {
//        logger.info(">>>>>>>>>>>>>manager:{}", manager);
//        response.addCookie(new Cookie("auth", "abc"));
//        return Result.success();
//    }

    @GetMapping
    public Result<Page<UserOrderCollectDTO>> list(String nameOrPhone,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end,
                                                  Pageable pageable) {
        logger.info("nameOrPhone:{}", nameOrPhone);
        logger.info("begin:{}", begin);
        logger.info("end:{}", end);
        String header = "";//TODO
        TimeRange timeRange = null;//TimeRange.from(begin, end);
        Page<UserOrderCollectDTO> page = userRepository.pageUserOrderCollectDTO(nameOrPhone, timeRange, null, pageable);
        return Result.success(page);
    }

    @GetMapping("{userId}/today")
    public Result<UserOrderCollectByDateDTO> listToday(@PathVariable long userId) {
        List<Long> stores = null;
        return Result.success(userService.getForToday(userId, stores));
    }

    @GetMapping("{userId}/week")
    public Result<List<UserOrderCollectByDateDTO>> listWeek(@PathVariable long userId) {
        List<Long> stores = null;
        return Result.success(userService.listForWeek(userId, stores));
    }

    @GetMapping("{userId}/month")
    public Result<List<UserOrderCollectByDateDTO>> listMonth(@PathVariable long userId) {
        List<Long> stores = null;
        return Result.success(userService.listForMonth(userId, stores));
    }

    @GetMapping("{userId}/season")
    public Result<List<UserOrderCollectByDateDTO>> listSeason(@PathVariable long userId) {
        List<Long> stores = null;
        return Result.success(userService.listForSeason(userId, stores));
    }

    @GetMapping("{userId}")
    public Result<List<UserOrderCollectByDateDTO>> find(
            @PathVariable long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate end
    ) {
        List<Long> stores = null;
        return Result.success(userService.listForDateRange(userId, begin, end, stores));
    }
}
