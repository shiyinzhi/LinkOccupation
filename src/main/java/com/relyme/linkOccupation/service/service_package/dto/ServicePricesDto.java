package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 套餐服务价格信息ServicePricesDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务价格信息ServicePricesDto", description = "套餐服务价格信息ServicePricesDto")
public class ServicePricesDto {

    /**
     * 服务价格uuid
     */
    @ApiModelProperty("服务价格uuid")
    private String uuid;

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务价格uuid")
    private String servicePackageUuid;

    /**
     * 企业人数下限
     */
    @ApiModelProperty("服务价格uuid")
    private int employeesLowerLimit;

    /**
     * 企业人数上限
     */
    @ApiModelProperty("服务价格uuid")
    private int employeesUpperLimit;

    /**
     * 月价格
     */
    @ApiModelProperty("服务价格uuid")
    private BigDecimal monthPrice;

    /**
     * 年价格
     */
    @ApiModelProperty("服务价格uuid")
    private BigDecimal yearPrice;

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
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

    public BigDecimal getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(BigDecimal monthPrice) {
        this.monthPrice = monthPrice;
    }

    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
