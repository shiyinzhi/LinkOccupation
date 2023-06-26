package com.relyme.linkOccupation.service.legal_advice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 法律咨询信息legalAdviceDto
 * @author shiyinzhi
 */
@ApiModel(value = "法律咨询信息legalAdviceDto", description = "法律咨询信息legalAdviceDto")
public class LegalAdviceDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("当前页数")
    private String custAccountUuid;

    /**
     * 企业UUID
     */
    @ApiModelProperty("当前页数")
    private String enterpriseUuid;

    /**
     * 咨询内容
     */
    @ApiModelProperty("咨询内容")
    private String legalContent;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;


    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getLegalContent() {
        return legalContent;
    }

    public void setLegalContent(String legalContent) {
        this.legalContent = legalContent;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
