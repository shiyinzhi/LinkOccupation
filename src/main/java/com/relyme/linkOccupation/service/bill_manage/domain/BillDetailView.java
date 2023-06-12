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
@Table(name = "view_bill_detail",indexes = {
        @Index(columnList = "uuid,employee_uuid")
})
public class BillDetailView extends BaseEntityForMysql {

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

    /**
     * 名称
     */
    @Column(name = "employee_name",length = 150)
    private String employeeName;

    /**
     * 身份证号码
     */
    @Column(name = "idcard_no",length = 128)
    private String idcardNo;

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 工种uuid
     */
    @Column(name = "employment_type_uuid", length = 36)
    private String employmentTypeUuid;

    /**
     * 任务名称
     */
    @Column(name = "mission_name",length = 128)
    private String missionName;

    /**
     * 任务金额
     */
    @Column(name = "mission_price",length = 11,scale = 2)
    private BigDecimal missionPrice;


    /**
     * 任务金额 最大金额
     */
    @Column(name = "mission_max_price",length = 11,scale = 2)
    private BigDecimal missionMaxPrice;

    /**
     * 分类名称
     */
    @Column(name = "type_name",length = 128,nullable = false)
    private String typeName;

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public BigDecimal getMissionPrice() {
        return missionPrice;
    }

    public void setMissionPrice(BigDecimal missionPrice) {
        this.missionPrice = missionPrice;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getMissionMaxPrice() {
        return missionMaxPrice;
    }

    public void setMissionMaxPrice(BigDecimal missionMaxPrice) {
        this.missionMaxPrice = missionMaxPrice;
    }
}
