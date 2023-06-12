package com.relyme.linkOccupation.service.enterpriseinfo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业雇主信息EnterpriseInfoQueryCustUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业雇主信息EnterpriseInfoQueryCustUuidDto", description = "企业雇主信息EnterpriseInfoQueryCustUuidDto")
public class EnterpriseInfoQueryCustUuidDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("用户uuid")
    private String custAccountUuid;

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
