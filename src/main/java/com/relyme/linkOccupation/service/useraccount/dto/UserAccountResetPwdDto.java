package com.relyme.linkOccupation.service.useraccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户账户信息UserAccountResetPwdDto
 * @author shiyinzhi
 */
@ApiModel(value = "用户账户信息UserAccountResetPwdDto", description = "用户账户信息UserAccountResetPwdDto")
public class UserAccountResetPwdDto {


    /**
     * 用户uuid
     */
    @ApiModelProperty("用户uuid")
    private String userAccountUuid;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
