package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务ServicePackageUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务ServicePackageUuidDto", description = "套餐服务ServicePackageUuidDto")
public class ServicePackageUuidDto {

    /**
     * 工资信息uuid
     */
    @ApiModelProperty("工资信息uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
