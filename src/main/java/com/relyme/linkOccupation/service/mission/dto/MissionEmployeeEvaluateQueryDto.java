package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务评价MissionEmployerEvaluateQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务评价MissionEmployeeEvaluateQueryDto", description = "任务评价MissionEmployeeEvaluateQueryDto")
public class MissionEmployeeEvaluateQueryDto {

    /**
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;


    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
