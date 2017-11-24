package com.yd.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/first")
public class FirstController {

    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        return "Hello World.";
    }
}
