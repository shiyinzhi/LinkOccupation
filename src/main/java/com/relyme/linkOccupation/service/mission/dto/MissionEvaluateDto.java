package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评价信息MissionEvaluateDto
 * @author shiyinzhi
 */
@ApiModel(value = "评价信息MissionEvaluateDto", description = "评价信息MissionEvaluateDto")
public class MissionEvaluateDto {

    /**
     * 其他描述
     */
    @ApiModelProperty("其他描述")
    private String remark;

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String missionUuid;

    /**
     * 评价者uuid
     */
    @ApiModelProperty("评价者uuid")
    private String evaluateFromUuid;


    /**
     * 被评价者uuid
     */
    @ApiModelProperty("被评价者uuid")
    private String evaluateToUuid;

    /**
     * 评价分数
     */
    @ApiModelProperty("评价分数")
    private int evaluateScore;


    /**
     * 评价者类型 1雇员 2雇主
     */
    @ApiModelProperty("评价者类型 1雇员 2雇主")
    private Integer evaluaterType;


    /**
     * 评价内容，多个使用逗号分隔
     */
    @ApiModelProperty("评价内容，多个使用逗号分隔")
    private String evaluateRontent;

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

    public String getEvaluateToUuid() {
        return evaluateToUuid;
    }

    public void setEvaluateToUuid(String evaluateToUuid) {
        this.evaluateToUuid = evaluateToUuid;
    }

    public Integer getEvaluaterType() {
        return evaluaterType;
    }

    public void setEvaluaterType(Integer evaluaterType) {
        this.evaluaterType = evaluaterType;
    }
}
