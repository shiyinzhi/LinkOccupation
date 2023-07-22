package com.relyme.linkOccupation.service.enterpriseinfo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业雇主信息EnterpriseInfoQueryContanctPhoneUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业雇主信息EnterpriseInfoQueryContanctPhoneUuidDto", description = "企业雇主信息EnterpriseInfoQueryContanctPhoneUuidDto")
public class EnterpriseInfoQueryContanctPhoneUuidDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * 企业联系电话 多个使用英文逗号分隔
     */
    @ApiModelProperty("企业联系电话 多个使用英文逗号分隔")
    private String contactPhone;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
