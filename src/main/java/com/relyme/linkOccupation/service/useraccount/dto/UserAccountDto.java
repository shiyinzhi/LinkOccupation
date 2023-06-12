package com.relyme.linkOccupation.service.useraccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 用户账户信息Dto
 * @author shiyinzhi
 */
@ApiModel(value = "用户账户信息Dto", description = "用户账户信息Dto")
public class UserAccountDto {

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 密码 使用MD5 加密
     */
    @ApiModelProperty("密码 使用MD5 加密")
    private String pwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(@NotNull String mobile) {
        this.mobile = mobile;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(@NotNull String pwd) {
        this.pwd = pwd;
    }
}
