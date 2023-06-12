package com.relyme.linkOccupation.service.common.differentstatus.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 异动状态信息
 * @author shiyinzhi
 */
@ApiModel(value = "异动状态AddDifferentStatusDto", description = "异动状态AddDifferentStatusDto")
public class AddDifferentStatusDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时传入")
    private String uuid;


    /**
     * 异动状态名称
     */
    @ApiModelProperty("异动状态名称")
    private String differentStatusName;


    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
