package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigInteger;

public class StatisticTotal extends BaseEntityForMysql {

    private String dayStr = "";
    private BigInteger employeeAdd = new BigInteger("0");
    private BigInteger employeeTotal = new BigInteger("0");
    private BigInteger individualEmployerAdd = new BigInteger("0");
    private BigInteger individualEmployerTotal = new BigInteger("0");
    private BigInteger enterpriseInfoAdd = new BigInteger("0");
    private BigInteger enterpriseInfoTotal = new BigInteger("0");
    private BigInteger allTotal = new BigInteger("0");

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public BigInteger getEmployeeAdd() {
        return employeeAdd;
    }

    public void setEmployeeAdd(BigInteger employeeAdd) {
        this.employeeAdd = employeeAdd;
    }

    public BigInteger getEmployeeTotal() {
        return employeeTotal;
    }

    public void setEmployeeTotal(BigInteger employeeTotal) {
        this.employeeTotal = employeeTotal;
    }

    public BigInteger getIndividualEmployerAdd() {
        return individualEmployerAdd;
    }

    public void setIndividualEmployerAdd(BigInteger individualEmployerAdd) {
        this.individualEmployerAdd = individualEmployerAdd;
    }

    public BigInteger getIndividualEmployerTotal() {
        return individualEmployerTotal;
    }

    public void setIndividualEmployerTotal(BigInteger individualEmployerTotal) {
        this.individualEmployerTotal = individualEmployerTotal;
    }

    public BigInteger getEnterpriseInfoAdd() {
        return enterpriseInfoAdd;
    }

    public void setEnterpriseInfoAdd(BigInteger enterpriseInfoAdd) {
        this.enterpriseInfoAdd = enterpriseInfoAdd;
    }

    public BigInteger getEnterpriseInfoTotal() {
        return enterpriseInfoTotal;
    }

    public void setEnterpriseInfoTotal(BigInteger enterpriseInfoTotal) {
        this.enterpriseInfoTotal = enterpriseInfoTotal;
    }

    public BigInteger getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(BigInteger allTotal) {
        this.allTotal = allTotal;
    }
}
