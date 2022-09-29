package com.heshuang.businessstart.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminSmsLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @NotEmpty
    @ApiModelProperty(value = "验证码", required = true)
    private String smsCode;
}
