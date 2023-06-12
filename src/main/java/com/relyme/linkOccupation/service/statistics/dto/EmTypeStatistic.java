package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigInteger;

public class EmTypeStatistic extends BaseEntityForMysql {

    private String emType;
    private BigInteger totalCount;

    public String getEmType() {
        return emType;
    }

    public void setEmType(String emType) {
        this.emType = emType;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }
}
