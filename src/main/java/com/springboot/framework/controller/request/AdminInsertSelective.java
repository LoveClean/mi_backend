package com.springboot.framework.controller.request;

import lombok.Data;

@Data
public class AdminInsertSelective {
    private String account;
    private String password;
    private String phone;
    private String name;
}
