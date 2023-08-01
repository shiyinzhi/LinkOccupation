package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务订单ServiceOrderUuidDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务订单ServiceOrderUuidDelDto", description = "套餐服务订单ServiceOrderUuidDelDto")
public class ServiceOrderUuidDelDto {

    /**
     * 服务订单uuid
     */
    @ApiModelProperty("服务订单uuid")
    private String uuid;

    /**
     *备注
     */
    @ApiModelProperty("备注")
    private String remark;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
