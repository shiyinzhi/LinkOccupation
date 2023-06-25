package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务优惠信息ServiceSpecialOfferDescQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务优惠信息ServiceSpecialOfferDescQueryDto", description = "套餐服务优惠信息ServiceSpecialOfferDescQueryDto")
public class ServiceSpecialOfferDescQueryDto {

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务套餐uuid")
    private String servicePackageUuid;

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }
}
