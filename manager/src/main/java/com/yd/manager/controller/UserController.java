package com.yd.manager.controller;

import com.yd.manager.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController extends CommonController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

//    @GetMapping
//    public Result<Boolean> login(HttpServletResponse response, @RequestHeader(required = false, defaultValue = "") String manager) {
//        logger.info(">>>>>>>>>>>>>manager:{}", manager);
//        response.addCookie(new Cookie("auth", "abc"));
//        return Result.success();
//    }

    @GetMapping
    public Result<Page<UserOrderCollectDTO>> list(String nameOrPhone,
                                                  LocalDate begin,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                  Pageable pageable) {
        logger.info("nameOrPhone:{}", nameOrPhone);
        logger.info("begin:{}", begin);
        logger.info("nameOrPhone:{}", nameOrPhone);
        String header = "";//TODO
        TimeRange timeRange = null;//TimeRange.from(begin, end);
        Page<UserOrderCollectDTO> page = userRepository.pageUserOrderCollectDTO(nameOrPhone, timeRange, null, pageable);
        return Result.success(page);
    }

}
