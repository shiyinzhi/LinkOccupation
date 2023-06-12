package com.relyme.linkOccupation.service.sensitive_word.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 敏感词信息SensitiveWordDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "敏感词信息SensitiveWordDelDto", description = "敏感词信息SensitiveWordDelDto")
public class SensitiveWordDelDto {


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
