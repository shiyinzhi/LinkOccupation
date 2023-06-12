package com.relyme.linkOccupation.service.common.department.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "部门信息DepartmentInfoApiListsDto", description = "部门信息DepartmentInfoApiListsDto")
public class DepartmentInfoApiListsDto {

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("父级部门uuid")
    private String parentDepartmentUuid;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getParentDepartmentUuid() {
        return parentDepartmentUuid;
    }

    public void setParentDepartmentUuid(String parentDepartmentUuid) {
        this.parentDepartmentUuid = parentDepartmentUuid;
    }
}
