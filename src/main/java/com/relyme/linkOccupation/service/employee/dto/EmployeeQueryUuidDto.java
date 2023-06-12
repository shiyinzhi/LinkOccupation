package com.relyme.linkOccupation.service.employee.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇员信息EmployeeQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员信息EmployeeQueryUuidDto", description = "雇员信息EmployeeQueryUuidDto")
public class EmployeeQueryUuidDto {

    /**
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
