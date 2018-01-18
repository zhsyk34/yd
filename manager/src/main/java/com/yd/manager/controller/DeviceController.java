package com.yd.manager.controller;

import com.yd.manager.dto.device.StoreDevice;
import com.yd.manager.dto.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yd.manager.service.DeviceService.SUPER_ACCOUNT;

@RestController
@RequestMapping("devices")
public class DeviceController extends CommonController {

    @GetMapping
    public Result<List<StoreDevice>> listAll() {
        return Result.success(deviceService.listStoreDevice(SUPER_ACCOUNT));
    }

    @GetMapping("{name}")
    public Result<StoreDevice> getByName(@PathVariable String name) {
        return Result.success(deviceService.getStoreDeviceByName(SUPER_ACCOUNT, name));
    }

}
