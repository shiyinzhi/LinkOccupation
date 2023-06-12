package com.relyme.linkOccupation.service.common.department.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "部门信息DepartmentInfoListsDto", description = "部门信息DepartmentInfoListsDto")
public class DepartmentInfoListsDto {

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("父级部门uuid")
    private String parentDepartmentUuid;

    @ApiModelProperty("企业编号uuid")
    private String enterpriseUuid;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getParentDepartmentUuid() {
        return parentDepartmentUuid;
    }

    public void setParentDepartmentUuid(String parentDepartmentUuid) {
        this.parentDepartmentUuid = parentDepartmentUuid;
    }
}
