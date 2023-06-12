package com.relyme.linkOccupation.service.legal_consulting.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 法律咨询管理LegalConsultingUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "法律咨询管理LegalConsultingUpdateDto", description = "法律咨询管理LegalConsultingUpdateDto")
public class LegalConsultingUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 正文
     */
    @ApiModelProperty("正文")
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
