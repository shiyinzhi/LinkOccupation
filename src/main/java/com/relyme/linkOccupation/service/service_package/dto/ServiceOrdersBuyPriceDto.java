package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务订单信息ServiceOrdersBuyPriceDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务订单信息ServiceOrdersBuyPriceDto", description = "套餐服务订单信息ServiceOrdersBuyPriceDto")
public class ServiceOrdersBuyPriceDto {

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 服务价格UUID
     */
    @ApiModelProperty("服务价格UUID")
    private String servicePricesUuid;


    /**
     * 优惠活动UUID
     */
    @ApiModelProperty("优惠活动UUID")
    private String serviceSpecialOfferUuid;

    /**
     * 购买类型 1 按月购买 2按年购买
     */
    @ApiModelProperty("购买类型 1 按月购买 2按年购买")
    private int buyType;

    /**
     * 优惠活动类型 0 体验包 1 充值好礼
     */
    @ApiModelProperty("优惠活动类型 0 体验包 1 充值好礼")
    private Integer specialType;

    /**
     * 购买数量
     */
    @ApiModelProperty("购买数量")
    private int buyNum;


    public String getServiceSpecialOfferUuid() {
        return serviceSpecialOfferUuid;
    }

    public void setServiceSpecialOfferUuid(String serviceSpecialOfferUuid) {
        this.serviceSpecialOfferUuid = serviceSpecialOfferUuid;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public String getServicePricesUuid() {
        return servicePricesUuid;
    }

    public void setServicePricesUuid(String servicePricesUuid) {
        this.servicePricesUuid = servicePricesUuid;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }
}
