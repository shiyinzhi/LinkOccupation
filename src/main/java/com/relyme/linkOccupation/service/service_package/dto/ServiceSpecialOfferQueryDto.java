package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务优惠信息ServiceSpecialOfferQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务优惠信息ServiceSpecialOfferQueryDto", description = "套餐服务优惠信息ServiceSpecialOfferQueryDto")
public class ServiceSpecialOfferQueryDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务套餐uuid")
    private String servicePackageUuid;

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

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }
}
