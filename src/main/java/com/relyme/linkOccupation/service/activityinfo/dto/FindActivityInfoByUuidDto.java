package com.relyme.linkOccupation.service.activityinfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动信息FindActivityInfoByUuidDto", description = "活动信息FindActivityInfoByUuidDto")
public class FindActivityInfoByUuidDto {

    @ApiModelProperty("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
