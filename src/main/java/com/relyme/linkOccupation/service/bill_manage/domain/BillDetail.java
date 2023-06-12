package com.relyme.linkOccupation.service.bill_manage.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 账单明细信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "bill_detail",indexes = {
        @Index(columnList = "uuid,employee_uuid")
})
public class BillDetail extends BaseEntityForMysql {

    /**
     * 雇员uuid
     */
    @Column(name = "employee_uuid", length = 36)
    private String employeeUuid;

    /**
     * 任务uuid
     */
    @Column(name = "mission_uuid", length = 36)
    private String missionUuid;

    /**
     * 获取金额
     */
    @Column(name = "earn_money",length = 11,scale = 2)
    private BigDecimal earnMoney;


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

    public BigDecimal getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(BigDecimal earnMoney) {
        this.earnMoney = earnMoney;
    }

    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }
}
