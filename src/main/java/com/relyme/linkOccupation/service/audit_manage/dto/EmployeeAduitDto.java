package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇员EmployeeAduitDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员EmployeeAduitDto", description = "雇员EmployeeAduitDto")
public class EmployeeAduitDto {


    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    @ApiModelProperty("1通过 2审核不通过")
    private Integer isAudit;

    @ApiModelProperty("审核不通过原因")
    private String remark;

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
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
