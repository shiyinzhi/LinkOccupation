package com.relyme.linkOccupation.service.employee.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇员信息EmployeeQueryCustUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员信息EmployeeQueryCustUuidDto", description = "雇员信息EmployeeQueryCustUuidDto")
public class EmployeeQueryCustUuidDto {

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
