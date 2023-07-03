package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 代企业账户信息BuyerAccountStatusDto
 * @author shiyinzhi
 */
@ApiModel(value = "代企业账户信息BuyerAccountStatusDto", description = "代企业账户信息BuyerAccountStatusDto")
public class BuyerAccountStatusDto {

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
