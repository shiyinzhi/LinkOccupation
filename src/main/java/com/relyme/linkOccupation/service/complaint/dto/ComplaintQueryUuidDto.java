package com.relyme.linkOccupation.service.complaint.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉建议信息ComplaintQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "投诉建议信息ComplaintQueryUuidDto", description = "投诉建议信息ComplaintQueryUuidDto")
public class ComplaintQueryUuidDto {

    /**
     * 投诉建议uuid
     */
    @ApiModelProperty("投诉建议uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
