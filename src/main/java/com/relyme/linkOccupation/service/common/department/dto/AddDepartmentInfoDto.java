package com.relyme.linkOccupation.service.common.department.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部门信息
 * @author shiyinzhi
 */
@ApiModel(value = "部门信息AddDepartmentInfoDto", description = "部门信息AddDepartmentInfoDto")
public class AddDepartmentInfoDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时传入")
    private String uuid;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;


    /**
     * 父级部门 uuid
     */
    @ApiModelProperty("父级部门 uuid")
    private String parentDepartmentUuid;


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getParentDepartmentUuid() {
        return parentDepartmentUuid;
    }

    public void setParentDepartmentUuid(String parentDepartmentUuid) {
        this.parentDepartmentUuid = parentDepartmentUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
