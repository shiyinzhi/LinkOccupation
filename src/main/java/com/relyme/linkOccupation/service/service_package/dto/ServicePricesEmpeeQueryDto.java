package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务价格信息ServicePricesEmpeeQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务价格信息ServicePricesEmpeeQueryDto", description = "套餐服务价格信息ServicePricesEmpeeQueryDto")
public class ServicePricesEmpeeQueryDto{


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
