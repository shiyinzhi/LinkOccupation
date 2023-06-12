package com.relyme.linkOccupation.service.mission.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇主信息EmployerQueryCustUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇主信息EmployerQueryCustUuidDto", description = "雇主信息EmployerQueryCustUuidDto")
public class EmployerQueryCustUuidDto {

    /**
     * 用户uuid
     */
    @ApiModelProperty("用户uuid")
    private String custAccountUuid;

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
