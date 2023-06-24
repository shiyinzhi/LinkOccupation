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
}
