package com.yd.manager.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @GetMapping
    public String list(HttpServletResponse response) {
        Cookie user = new Cookie("user", "100");
//        response.addCookie(user);
        response.addCookie(new Cookie("warn", new String("小何还是幼稚啊".getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII)));
        return "hello";
    }
}
