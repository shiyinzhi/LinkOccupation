package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务价格ServicePricesUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务价格ServicePricesUuidDto", description = "套餐服务价格ServicePricesUuidDto")
public class ServicePricesUuidDto {

    /**
     * 服务价格uuid
     */
    @ApiModelProperty("服务价格uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
