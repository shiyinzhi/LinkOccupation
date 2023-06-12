package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigDecimal;

public class EmTopMoneyStatistic extends BaseEntityForMysql {

    private String emName;
    private BigDecimal totalMoney;

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
