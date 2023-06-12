package com.relyme.linkOccupation.service.enterpriseinfo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业雇主信息EnterpriseInfoQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业雇主信息EnterpriseInfoQueryUuidDto", description = "企业雇主信息EnterpriseInfoQueryUuidDto")
public class EnterpriseInfoQueryUuidDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
