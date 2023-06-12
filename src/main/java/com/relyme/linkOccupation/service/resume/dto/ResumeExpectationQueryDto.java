package com.relyme.linkOccupation.service.resume.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人简历期望信息ResumeExpectationQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历期望信息ResumeExpectationQueryDto", description = "个人简历期望信息ResumeExpectationQueryDto")
public class ResumeExpectationQueryDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("用户uuid")
    private String custAccountUuid;

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
