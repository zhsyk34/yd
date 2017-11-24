package com.yd.manager.controller;

import com.yd.manager.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController extends CommonController {

    @GetMapping
    public Result<Boolean> login(HttpServletResponse response, @RequestHeader(required = false, defaultValue = "") String manager) {
        logger.info(">>>>>>>>>>>>>manager:{}", manager);
        response.addCookie(new Cookie("auth", "abc"));
        return Result.success();
    }

}
