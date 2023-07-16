package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 套餐服务订单信息ServiceOrdersBuyPriceBackGroundDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务订单信息ServiceOrdersBuyPriceBackGroundDto", description = "套餐服务订单信息ServiceOrdersBuyPriceBackGroundDto")
public class ServiceOrdersBuyPriceBackGroundDto {

    /**
     * 订单UUID
     */
    @ApiModelProperty("订单UUID")
    private String uuid;

    /**
     * 后台折扣
     */
    @ApiModelProperty("后台折扣")
    private BigDecimal backgroundDiscounts;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getBackgroundDiscounts() {
        return backgroundDiscounts;
    }

    public void setBackgroundDiscounts(BigDecimal backgroundDiscounts) {
        this.backgroundDiscounts = backgroundDiscounts;
    }
}
