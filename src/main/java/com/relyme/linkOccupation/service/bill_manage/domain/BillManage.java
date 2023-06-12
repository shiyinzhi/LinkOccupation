package com.relyme.linkOccupation.service.bill_manage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 账单信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "bill_manage",indexes = {
        @Index(columnList = "uuid,employee_uuid")
})
public class BillManage extends BaseEntityForMysql {

    /**
     * 雇员uuid
     */
    @Column(name = "employee_uuid", length = 36)
    private String employeeUuid;

    /**
     * 提现金额
     */
    @Column(name = "withdraw_money",length = 11,scale = 2)
    private BigDecimal withdrawMoney;


    /**
     * 剩余金额
     */
    @Column(name = "balance_money",length = 11,scale = 2)
    private BigDecimal balanceMoney;

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

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }
}
