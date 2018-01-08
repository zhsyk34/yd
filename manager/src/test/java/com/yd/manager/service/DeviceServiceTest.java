package com.yd.manager.service;

import com.yd.manager.Application;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DeviceServiceTest {

    @Autowired
    private DeviceService service;

    private String account = "estore";
    private String deviceSerial = "H10017CD09";

    private String result;

    @Test
    public void getScreenshot() {
        result = service.getScreenshot(account, deviceSerial, 1);
    }

    @Test
    public void getVideo() {
        result = service.getVideo(deviceSerial, 1);
    }

    @Test
    public void getDeviceList() {
        result = service.getDeviceList(account, 1, 10);
    }

    @Test
    public void getAccountList() {
        result = service.getAccountList(1, 10);
    }

    @After
    public void tearDown() {
//        System.out.println(result);
    }
}