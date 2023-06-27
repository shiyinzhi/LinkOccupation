package com.relyme.linkOccupation.service.complaint.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉建议信息ComplaintSatisfiedUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "投诉建议信息ComplaintSatisfiedUuidDto", description = "投诉建议信息ComplaintSatisfiedUuidDto")
public class ComplaintSatisfiedUuidDto {

    /**
     * 投诉建议uuid
     */
    @ApiModelProperty("投诉建议uuid")
    private String uuid;

    /**
     * 是否满意  1不满意 2满意
     */
    @ApiModelProperty("是否满意  1不满意 2满意")
    private Integer handleSatisfied;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getHandleSatisfied() {
        return handleSatisfied;
    }

    public void setHandleSatisfied(Integer handleSatisfied) {
        this.handleSatisfied = handleSatisfied;
    }
}
