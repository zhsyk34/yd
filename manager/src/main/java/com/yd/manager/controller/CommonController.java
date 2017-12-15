package com.yd.manager.controller;

import com.yd.manager.service.*;
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

    @Setter(onMethod = @__({@Autowired}))
    protected ManagerService managerService;
}
