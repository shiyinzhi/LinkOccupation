package com.relyme.linkOccupation.service.statistics.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CostsAndSalariesList extends BaseEntityForMysql {

    public CostsAndSalariesList(){}

    public CostsAndSalariesList(BigDecimal costTotal,BigInteger personCount, Date salaryMonth) {
        this.costTotal = costTotal;
        this.salaryMonth = salaryMonth;
        this.personCount = personCount;
    }

    private BigDecimal salaryTrueTotal;
    private BigDecimal insuranceTotal;
    private BigDecimal costTotal;

    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    private Date salaryMonth;

    private BigInteger personCount;

    public BigInteger getPersonCount() {
        return personCount;
    }

    public void setPersonCount(BigInteger personCount) {
        this.personCount = personCount;
    }

    public BigDecimal getSalaryTrueTotal() {
        return salaryTrueTotal;
    }

    public void setSalaryTrueTotal(BigDecimal salaryTrueTotal) {
        this.salaryTrueTotal = salaryTrueTotal;
    }

    public BigDecimal getInsuranceTotal() {
        return insuranceTotal;
    }

    public void setInsuranceTotal(BigDecimal insuranceTotal) {
        this.insuranceTotal = insuranceTotal;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    public Date getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Date salaryMonth) {
        this.salaryMonth = salaryMonth;
    }
}
