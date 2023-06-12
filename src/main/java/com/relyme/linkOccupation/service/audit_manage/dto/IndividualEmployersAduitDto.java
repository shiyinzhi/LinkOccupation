package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人雇主信息IndividualEmployersAduitDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人雇主信息IndividualEmployersAduitDto", description = "个人雇主信息IndividualEmployersAduitDto")
public class IndividualEmployersAduitDto {


    @ApiModelProperty("个人雇主uuid")
    private String IndividualEmployersUuid;

    @ApiModelProperty("1通过 2审核不通过")
    private Integer isAudit;

    @ApiModelProperty("审核不通过原因")
    private String remark;

    public String getIndividualEmployersUuid() {
        return IndividualEmployersUuid;
    }

    public void setIndividualEmployersUuid(String individualEmployersUuid) {
        IndividualEmployersUuid = individualEmployersUuid;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
