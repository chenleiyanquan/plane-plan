package com.macro.mall.tiny.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginReq {
    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信验证码")
    private String verifyCode;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("登录标志：1、登录")
    private Integer isLogin;

}
