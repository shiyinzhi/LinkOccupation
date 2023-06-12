package com.relyme.linkOccupation.service.custaccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 患者账户信息SearchDto
 * @author shiyinzhi
 */
@ApiModel(value = "用户账户信息SearchDto", description = "用户账户信息SearchDto")
public class CustAccountSearchDto {

    /**
     * uuid
     */
    @ApiModelProperty("用户openid")
    private String openid;

    /**
     * 用户类型 1雇员 2个人雇主 3企业雇主
     */
    @ApiModelProperty("用户类型 1雇员 2个人雇主 3企业雇主")
    private Integer userType;

    /**
     * 手机号码
     */
    @ApiModelProperty("用户手机号码")
    private String mobile;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
