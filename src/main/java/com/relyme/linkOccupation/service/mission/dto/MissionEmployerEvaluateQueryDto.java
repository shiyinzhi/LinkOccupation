package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务评价MissionEmployerEvaluateQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "任务评价MissionEmployerEvaluateQueryDto", description = "任务评价MissionEmployerEvaluateQueryDto")
public class MissionEmployerEvaluateQueryDto {

    /**
     * 雇主uuid
     */
    @ApiModelProperty("雇主uuid")
    private String employerUuid;

    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;


    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
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
