package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业信息EnterpriseInfoDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人雇主信息IndividualEmployersDto", description = "个人雇主信息IndividualEmployersDto")
public class IndividualEmployersDto {


    @ApiModelProperty("个人雇主uuid")
    private String IndividualEmployersUuid;

    public String getIndividualEmployersUuid() {
        return IndividualEmployersUuid;
    }

    public void setIndividualEmployersUuid(String individualEmployersUuid) {
        IndividualEmployersUuid = individualEmployersUuid;
    }
}
