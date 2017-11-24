package com.yd.manager.controller;

import com.yd.manager.entity.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("managers")
@Slf4j
public class ManagerController extends CommonController {

    @GetMapping
    public Manager list() {
        return managerRepository.findOne(5L);
    }
}
