package com.relyme.linkOccupation.service.common.differentstatus.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "异动状态DifferentStatusListsDto", description = "异动状态DifferentStatusListsDto")
public class DifferentStatusListsDto {

    @ApiModelProperty("移动状态名称")
    private String differentStatusName;

    @ApiModelProperty("企业编号uuid")
    private String enterpriseUuid;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
    }

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

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
