package com.relyme.linkOccupation.service.employment_type.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用工分类EmploymentTypeDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "用工分类EmploymentTypeDelDto", description = "用工分类EmploymentTypeDelDto")
public class EmploymentTypeDelDto {


    /**
     * 敏感词uuid
     */
    @ApiModelProperty("敏感词uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
