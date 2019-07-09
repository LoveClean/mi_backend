package com.springboot.framework.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class AdminLogin {
    /**
     * 用户名或手机号
     */
    @NotNull(message = "用户名或手机号，必填")
    private String loginKey;
    /**
     * 密码
     */
    @Length(min = 3, max = 16, message = "请输入3-16位密码")
    @NotNull(message = "密码，必填")
    private String loginPwd;
    /**
     * 验证码
     */
    @NotNull(message = "验证码，必填")
    private String verifyCode;
}
