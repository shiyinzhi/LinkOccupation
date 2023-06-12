package com.relyme.linkOccupation.service.custaccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户CustOpenIdDto
 * @author shiyinzhi
 */
@ApiModel(value = "用户CustOpenIdDto", description = "用户CustOpenIdDto")
public class CustOpenIdDto {

    /**
     * openid
     */
    @ApiModelProperty("openid")
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
