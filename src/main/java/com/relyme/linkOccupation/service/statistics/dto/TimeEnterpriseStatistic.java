package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigInteger;

public class TimeEnterpriseStatistic extends BaseEntityForMysql {

    private String goTime;
    private BigInteger totalEnterprise;

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public BigInteger getTotalEnterprise() {
        return totalEnterprise;
    }

    public void setTotalEnterprise(BigInteger totalEnterprise) {
        this.totalEnterprise = totalEnterprise;
    }
}
