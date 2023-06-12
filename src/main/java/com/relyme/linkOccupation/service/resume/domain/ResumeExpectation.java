package com.relyme.linkOccupation.service.resume.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 个人简历期望信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_expectation",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeExpectation extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;


    /**
     * 雇员uuid
     */
    @Column(name = "employee_uuid",length = 36)
    private String employeeUuid;

    /**
     * 工作城市
     */
    @Column(name = "working_city",length = 128)
    private String workingCity;

    /**
     * 期望职位
     */
    @Column(name = "employment_type_uuid",length = 36)
    private String employmentTypeUuid;

    @Transient
    private String employmentTypeName;

    /**
     * 期望薪资
     */
    @Column(name = "expectation_price",length = 18,scale = 2)
    private BigDecimal expectationPrice;

    /**
     * 是否立即接单
     */
    @Column(name = "join_now", length = 3,columnDefinition="tinyint default 0")
    private int joinNow;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getWorkingCity() {
        return workingCity;
    }

    public void setWorkingCity(String workingCity) {
        this.workingCity = workingCity;
    }

    public String getEmploymentTypeUuid() {
        return employmentTypeUuid;
    }

    public void setEmploymentTypeUuid(String employmentTypeUuid) {
        this.employmentTypeUuid = employmentTypeUuid;
    }

    public BigDecimal getExpectationPrice() {
        return expectationPrice;
    }

    public void setExpectationPrice(BigDecimal expectationPrice) {
        this.expectationPrice = expectationPrice;
    }

    public String getEmploymentTypeName() {
        return employmentTypeName;
    }

    public void setEmploymentTypeName(String employmentTypeName) {
        this.employmentTypeName = employmentTypeName;
    }

    public int getJoinNow() {
        return joinNow;
    }

    public void setJoinNow(int joinNow) {
        this.joinNow = joinNow;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
