package com.yd.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrdersController extends CommonController {

    public void list() {
        ordersRepository.count();
    }
}
