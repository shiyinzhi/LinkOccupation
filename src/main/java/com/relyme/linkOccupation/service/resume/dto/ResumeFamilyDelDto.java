package com.relyme.linkOccupation.service.resume.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人简历家庭信息ResumeFamilyDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历家庭信息ResumeFamilyDelDto", description = "个人简历家庭信息ResumeFamilyDelDto")
public class ResumeFamilyDelDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
