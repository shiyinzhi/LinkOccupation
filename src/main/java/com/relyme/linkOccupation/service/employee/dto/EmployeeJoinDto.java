package com.relyme.linkOccupation.service.employee.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇员加入EmployeeJoinDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员加入EmployeeJoinDto", description = "雇员加入EmployeeJoinDto")
public class EmployeeJoinDto {

    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    @ApiModelProperty("任务uuid")
    private String missionUuid;

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }
}
