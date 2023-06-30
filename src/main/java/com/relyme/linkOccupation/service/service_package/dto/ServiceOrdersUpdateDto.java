package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务订单信息ServiceOrdersUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务订单信息ServiceOrdersUpdateDto", description = "套餐服务订单信息ServiceOrdersUpdateDto")
public class ServiceOrdersUpdateDto {

    /**
     * 服务订单uuid
     */
    @ApiModelProperty("服务订单uuid")
    private String serviceOrdersUuid;

    /**
     * 已购买服务套餐uuid
     */
    @ApiModelProperty("已购买服务套餐uuid")
    private String servicePackageUuid;

    /**
     * 变更服务套餐uuid
     */
    @ApiModelProperty("变更服务套餐uuid")
    private String newServicePackageUuid;

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;


    /**
     * 企业人数下限
     */
    @ApiModelProperty("企业人数下限")
    private int employeesLowerLimit;

    /**
     * 企业人数上限
     */
    @ApiModelProperty("企业人数上限")
    private int employeesUpperLimit;

    /**
     * 优惠活动类型 0 体验包 1 充值好礼
     */
    @ApiModelProperty("优惠活动类型 0 体验包 1 充值好礼")
    private Integer specialType;

    /**
     * 购买类型 1 按月购买 2按年购买
     */
    @ApiModelProperty("购买类型 1 按月购买 2按年购买")
    private int buyType;

    /**
     * 购买数量
     */
    @ApiModelProperty("购买数量")
    private int buyNum;

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public int getEmployeesLowerLimit() {
        return employeesLowerLimit;
    }

    public void setEmployeesLowerLimit(int employeesLowerLimit) {
        this.employeesLowerLimit = employeesLowerLimit;
    }

    public int getEmployeesUpperLimit() {
        return employeesUpperLimit;
    }

    public void setEmployeesUpperLimit(int employeesUpperLimit) {
        this.employeesUpperLimit = employeesUpperLimit;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }

    public String getNewServicePackageUuid() {
        return newServicePackageUuid;
    }

    public void setNewServicePackageUuid(String newServicePackageUuid) {
        this.newServicePackageUuid = newServicePackageUuid;
    }

    public String getServiceOrdersUuid() {
        return serviceOrdersUuid;
    }

    public void setServiceOrdersUuid(String serviceOrdersUuid) {
        this.serviceOrdersUuid = serviceOrdersUuid;
    }
}
