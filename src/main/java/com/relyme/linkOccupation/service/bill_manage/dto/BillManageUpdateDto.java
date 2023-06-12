package com.relyme.linkOccupation.service.bill_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 账单信息BillManageUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "账单信息BillManageUpdateDto", description = "账单信息BillManageUpdateDto")
public class BillManageUpdateDto {


    /**
     * 雇员uuid
     */
    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    /**
     * 提现金额
     */
    @ApiModelProperty("提现金额")
    private BigDecimal withdrawMoney;

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }
}
