package com.yd.manager.controller;

import com.yd.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;

abstract class CommonController {
    @Autowired
    protected OrdersService ordersService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected StoreService storeService;
    @Autowired
    protected MerchandiseService merchandiseService;
    @Autowired
    protected ManagerService managerService;
    @Autowired
    protected AccessRecordService accessRecordService;
    @Autowired
    protected AccessAttachService accessAttachService;
    @Autowired
    protected DeviceService deviceService;
}
