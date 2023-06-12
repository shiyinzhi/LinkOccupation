package com.relyme.linkOccupation.service.common.workertype.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "员工类型WorkerTypeListsDto", description = "员工类型WorkerTypeListsDto")
public class WorkerTypeListsDto {

    @ApiModelProperty("员工类型名称")
    private String workerTypeName;

    @ApiModelProperty("企业编号uuid")
    private String enterpriseUuid;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

    public String getWorkerTypeName() {
        return workerTypeName;
    }

    public void setWorkerTypeName(String workerTypeName) {
        this.workerTypeName = workerTypeName;
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
