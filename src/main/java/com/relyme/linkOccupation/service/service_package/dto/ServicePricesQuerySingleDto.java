package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务价格信息ServicePricesQuerySingleDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务价格信息ServicePricesQuerySingleDto", description = "套餐服务价格信息ServicePricesQuerySingleDto")
public class ServicePricesQuerySingleDto {

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务套餐uuid")
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

}
