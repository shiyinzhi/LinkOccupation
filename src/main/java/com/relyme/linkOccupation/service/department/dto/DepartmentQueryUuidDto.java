package com.relyme.linkOccupation.service.department.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业部门信息DepartmentQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业部门信息DepartmentQueryUuidDto", description = "企业部门信息DepartmentQueryUuidDto")
public class DepartmentQueryUuidDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
