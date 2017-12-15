package com.yd.manager.controller;

import com.yd.manager.dto.util.ManagerInfo;
import com.yd.manager.dto.util.Result;
import com.yd.manager.util.EncryptUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("managers")
public class ManagerController extends CommonController {

    @PostMapping
    public Result<String> login(String username, String password) {
        ManagerInfo info = managerService.getManagerInfo(username, password);
        if (info == null) {
            return Result.from(HttpStatus.UNAUTHORIZED, "身份认证失败");
        }
        return Result.success(EncryptUtils.encodeToString(String.valueOf(info.getId())));
    }
}
