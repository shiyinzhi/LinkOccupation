package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评价信息MissionEvaluateMyDto
 * @author shiyinzhi
 */
@ApiModel(value = "评价信息MissionEvaluateMyDto", description = "评价信息MissionEvaluateMyDto")
public class MissionEvaluateMyDto {

    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 任务记录uuid
     */
    @ApiModelProperty("任务记录uuid")
    private String missionRecordUuid;

    /**
     * 评价者uuid  任务uuid
     */
    @ApiModelProperty("评价者uuid")
    private String evaluateFromUuid;


    public String getMissionRecordUuid() {
        return missionRecordUuid;
    }

    public void setMissionRecordUuid(String missionRecordUuid) {
        this.missionRecordUuid = missionRecordUuid;
    }

    public String getEvaluateFromUuid() {
        return evaluateFromUuid;
    }

    public void setEvaluateFromUuid(String evaluateFromUuid) {
        this.evaluateFromUuid = evaluateFromUuid;
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
