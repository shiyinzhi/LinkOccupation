package com.relyme.linkOccupation.service.socialsecurity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 代缴社保信息SocialSecurityUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "代缴社保信息SocialSecurityUuidDto", description = "代缴社保信息SocialSecurityUuidDto")
public class SocialSecurityUuidDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
