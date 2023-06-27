package com.relyme.linkOccupation.service.legal_advice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉建议信息LegalAdviceQueryUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "投诉建议信息LegalAdviceQueryUuidDto", description = "投诉建议信息LegalAdviceQueryUuidDto")
public class LegalAdviceQueryUuidDto {

    /**
     * 投诉建议uuid
     */
    @ApiModelProperty("投诉建议uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
