package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigInteger;

public class TimePersonStatistic extends BaseEntityForMysql {

    private String goTime;
    private String employeeUuid;
    private BigInteger totalPersons;

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public BigInteger getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(BigInteger totalPersons) {
        this.totalPersons = totalPersons;
    }
}
