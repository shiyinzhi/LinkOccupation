package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 审核管理AuditManageQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员EmployeeDto", description = "雇员EmployeeDto")
public class EmployeeDto {


    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
