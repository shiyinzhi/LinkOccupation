package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigDecimal;

public class TimePriceStatistic extends BaseEntityForMysql {

    private String goTime;
    private BigDecimal totalPrice;

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
