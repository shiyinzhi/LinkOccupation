package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务优惠ServiceSpecialOfferUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务优惠ServiceSpecialOfferUuidDto", description = "套餐服务优惠ServiceSpecialOfferUuidDto")
public class ServiceSpecialOfferUuidDto {

    /**
     * 服务优惠uuid
     */
    @ApiModelProperty("服务优惠uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
