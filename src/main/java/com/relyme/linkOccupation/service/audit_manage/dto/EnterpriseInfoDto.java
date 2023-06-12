package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业信息EnterpriseInfoDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业信息EnterpriseInfoDto", description = "企业信息EnterpriseInfoDto")
public class EnterpriseInfoDto {


    @ApiModelProperty("企业uuid")
    private String enterpriseInfoUuid;

    public String getEnterpriseInfoUuid() {
        return enterpriseInfoUuid;
    }

    public void setEnterpriseInfoUuid(String enterpriseInfoUuid) {
        this.enterpriseInfoUuid = enterpriseInfoUuid;
    }
}
