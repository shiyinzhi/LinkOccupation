package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评价信息MissionEvaluateDto
 * @author shiyinzhi
 */
@ApiModel(value = "评价信息MissionEvaluateAllDto", description = "评价信息MissionEvaluateAllDto")
public class MissionEvaluateAllDto {

    /**
     * 其他描述
     */
    @ApiModelProperty("其他描述")
    private String remark;

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid 全部一键评论传入")
    private String missionUuid;

    /**
     * 评价者uuid
     */
    @ApiModelProperty("评价者uuid")
    private String evaluateFromUuid;

    /**
     * 评价分数
     */
    @ApiModelProperty("评价分数")
    private int evaluateScore;

    /**
     * 评价内容，多个使用逗号分隔
     */
    @ApiModelProperty("评价内容，多个使用逗号分隔")
    private String evaluateRontent;

    /**
     * 评价的任务uuid，多个使用都好分隔
     */
    @ApiModelProperty("评价的任务uuid，多个使用都好分隔")
    private String missionRecordUuids;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public int getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(int evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public String getEvaluateRontent() {
        return evaluateRontent;
    }

    public void setEvaluateRontent(String evaluateRontent) {
        this.evaluateRontent = evaluateRontent;
    }

    public String getEvaluateFromUuid() {
        return evaluateFromUuid;
    }

    public void setEvaluateFromUuid(String evaluateFromUuid) {
        this.evaluateFromUuid = evaluateFromUuid;
    }

    public String getMissionRecordUuids() {
        return missionRecordUuids;
    }

    public void setMissionRecordUuids(String missionRecordUuids) {
        this.missionRecordUuids = missionRecordUuids;
    }
}
