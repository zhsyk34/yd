package com.yd.manager.dto.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhpData {
    private int code;
    private String msg;
    private ManagerInfo result;
}