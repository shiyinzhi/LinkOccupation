package com.relyme.linkOccupation.service.department.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业岗位信息DepartmentQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业岗位信息DepartmentQueryUuidDto", description = "企业岗位信息DepartmentQueryUuidDto")
public class PostQueryUuidDto {

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
