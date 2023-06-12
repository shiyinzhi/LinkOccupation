package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业雇主EnterpriseInfoAduitDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业雇主EnterpriseInfoAduitDto", description = "企业雇主EnterpriseInfoAduitDto")
public class EnterpriseInfoAduitDto {


    @ApiModelProperty("企业uuid")
    private String enterpriseInfoUuid;

    @ApiModelProperty("1通过 2审核不通过")
    private Integer isAudit;

    @ApiModelProperty("审核不通过原因")
    private String remark;

    public String getEnterpriseInfoUuid() {
        return enterpriseInfoUuid;
    }

    public void setEnterpriseInfoUuid(String enterpriseInfoUuid) {
        this.enterpriseInfoUuid = enterpriseInfoUuid;
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
