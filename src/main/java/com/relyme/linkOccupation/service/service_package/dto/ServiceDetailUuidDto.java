package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务详情ServiceDetailUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务详情ServiceDetailUuidDto", description = "套餐服务详情ServiceDetailUuidDto")
public class ServiceDetailUuidDto {

    /**
     * 服务详情uuid
     */
    @ApiModelProperty("服务详情uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
