package com.relyme.linkOccupation.service.socialsecurity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 代缴社保信息SocialSecurityUuidListDto
 * @author shiyinzhi
 */
@ApiModel(value = "代缴社保信息SocialSecurityUuidListDto", description = "代缴社保信息SocialSecurityUuidListDto")
public class SocialSecurityUuidListDto {

    /**
     * 任务uuid
     */
    @ApiModelProperty("任务uuid")
    private List<String> uuids;


    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }
}
