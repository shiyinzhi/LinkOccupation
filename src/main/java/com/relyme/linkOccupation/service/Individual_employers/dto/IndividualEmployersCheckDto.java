package com.relyme.linkOccupation.service.Individual_employers.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人雇主信息IndividualEmployersCheckDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人雇主信息IndividualEmployersCheckDto", description = "个人雇主信息IndividualEmployersCheckDto")
public class IndividualEmployersCheckDto {


    /**
     * 提交人/确认人 关联客户账户
     */
    @ApiModelProperty("关联客户账户")
    private String custAccountUuid;

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
