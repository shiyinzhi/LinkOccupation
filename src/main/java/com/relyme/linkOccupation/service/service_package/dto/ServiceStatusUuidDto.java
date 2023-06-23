package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务进度信息ServiceStatusUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusUuidDto", description = "服务进度信息ServiceStatusUuidDto")
public class ServiceStatusUuidDto {

    /**
     * 服务进度uuid
     */
    @ApiModelProperty("服务进度uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
