package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigDecimal;
import java.math.BigInteger;

public class AreaStatistic extends BaseEntityForMysql {

    private String cityCode;
    private String cityName;
    private BigDecimal totalPrice;
    private BigInteger totalPersons;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigInteger getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(BigInteger totalPersons) {
        this.totalPersons = totalPersons;
    }
}
