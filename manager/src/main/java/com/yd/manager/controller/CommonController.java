package com.yd.manager.controller;

import com.yd.manager.service.MerchandiseService;
import com.yd.manager.service.OrdersService;
import com.yd.manager.service.StoreService;
import com.yd.manager.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

abstract class CommonController {
    @Setter(onMethod = @__({@Autowired}))
    protected OrdersService ordersService;

    @Setter(onMethod = @__({@Autowired}))
    protected UserService userService;

    @Setter(onMethod = @__({@Autowired}))
    protected StoreService storeService;

    @Setter(onMethod = @__({@Autowired}))
    protected MerchandiseService merchandiseService;
}
