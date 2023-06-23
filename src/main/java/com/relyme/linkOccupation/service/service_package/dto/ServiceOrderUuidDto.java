package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务订单ServiceOrderUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务订单ServiceOrderUuidDto", description = "套餐服务订单ServiceOrderUuidDto")
public class ServiceOrderUuidDto {

    /**
     * 服务订单uuid
     */
    @ApiModelProperty("服务订单uuid")
    private String uuid;

    /**
     * 是否线下购买 0 否 1是
     */
    @ApiModelProperty("是否线下购买 0 否 1是")
    private int isBuyOffline;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getIsBuyOffline() {
        return isBuyOffline;
    }

    public void setIsBuyOffline(int isBuyOffline) {
        this.isBuyOffline = isBuyOffline;
    }
}
