package com.yd.manager.service;

import com.yd.manager.repository.SpringTestInit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerServiceTest extends SpringTestInit {

    @Autowired
    private ManagerService managerService;

    @Test
    public void test1() {
        managerService.getManagerInfo("郭龙彬", "123456");
    }
}