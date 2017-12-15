package com.yd.manager.dto.util;

import lombok.Data;

@Data
public class PhpData {
    private final int code;
    private final String msg;
    private final ManagerInfo result;
}