package com.relyme.linkOccupation.service.useraccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户账户信息UserAccountPwdUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "用户账户信息UserAccountPwdUpdateDto", description = "用户账户信息UserAccountPwdUpdateDto")
public class UserAccountPwdUpdateDto {

    /**
     * 原来的密码 使用MD5 加密
     */
    @ApiModelProperty("原来的密码 使用MD5 加密")
    private String oldPwd;

    /**
     * 密码 使用MD5 加密
     */
    @ApiModelProperty("密码 使用MD5 加密")
    private String newPwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
