package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务进度信息ServiceStatusExtDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusExtDelDto", description = "服务进度信息ServiceStatusExtDelDto")
public class ServiceStatusExtDelDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
