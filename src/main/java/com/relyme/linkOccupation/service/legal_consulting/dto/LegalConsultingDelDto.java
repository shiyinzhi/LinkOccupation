package com.relyme.linkOccupation.service.legal_consulting.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 法律咨询管理LegalConsultingDelDto
 * @author shiyinzhi
 */
@ApiModel(value = "法律咨询管理LegalConsultingDelDto", description = "法律咨询管理LegalConsultingDelDto")
public class LegalConsultingDelDto {


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
