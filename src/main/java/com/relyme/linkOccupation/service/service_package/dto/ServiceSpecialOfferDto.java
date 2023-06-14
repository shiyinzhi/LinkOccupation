package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 套餐服务优惠信息ServiceSpecialOfferDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务优惠信息ServiceSpecialOfferDto", description = "套餐服务优惠信息ServiceSpecialOfferDto")
public class ServiceSpecialOfferDto {

    /**
     * 服务优惠uuid
     */
    @ApiModelProperty("服务优惠uuid")
    private String uuid;

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务套餐uuid")
    private String servicePackageUuid;

    /**
     * 优惠活动类型 0 体验包 1 充值好礼
     */
    @ApiModelProperty("优惠活动类型 0 体验包 1 充值好礼")
    private int specialType;

    /**
     * 折扣 %
     */
    @ApiModelProperty("折扣 %")
    private BigDecimal serviceDiscounts;

    /**
     * 优惠月数
     */
    @ApiModelProperty("优惠月数")
    private int specialMonthes;

    /**
     * 优惠次数
     */
    @ApiModelProperty("优惠次数")
    private int specialCounts;

    /**
     * 购买年数
     */
    @ApiModelProperty("购买年数")
    private int buyYears;

    /**
     * 赠送月数
     */
    @ApiModelProperty("赠送月数")
    private int freeMonthes;

    /**
     * 是否下架 0上架 1下架
     */
    @ApiModelProperty("是否下架 0上架 1下架")
    private int isClose;


    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }

    public BigDecimal getServiceDiscounts() {
        return serviceDiscounts;
    }

    public void setServiceDiscounts(BigDecimal serviceDiscounts) {
        this.serviceDiscounts = serviceDiscounts;
    }

    public int getSpecialMonthes() {
        return specialMonthes;
    }

    public void setSpecialMonthes(int specialMonthes) {
        this.specialMonthes = specialMonthes;
    }

    public int getSpecialCounts() {
        return specialCounts;
    }

    public void setSpecialCounts(int specialCounts) {
        this.specialCounts = specialCounts;
    }

    public int getBuyYears() {
        return buyYears;
    }

    public void setBuyYears(int buyYears) {
        this.buyYears = buyYears;
    }

    public int getFreeMonthes() {
        return freeMonthes;
    }

    public void setFreeMonthes(int freeMonthes) {
        this.freeMonthes = freeMonthes;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
