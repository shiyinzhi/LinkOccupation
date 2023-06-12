package com.relyme.linkOccupation.service.sensitive_word.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 敏感词信息SensitiveWordUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "敏感词信息SensitiveWordUpdateDto", description = "敏感词信息SensitiveWordUpdateDto")
public class SensitiveWordUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 敏感词内容
     */
    @ApiModelProperty("敏感词内容")
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
