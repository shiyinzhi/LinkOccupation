package com.relyme.linkOccupation.service.statistics.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 首页数据配置
 * @author shiyinzhi
 */
@Entity
@Table(name = "cust_data_for_area",indexes = {
        @Index(columnList = "uuid,city_code,city_name")
})
public class CustDataForArea extends BaseEntityForMysql {

    @Column(name = "city_code",length = 15)
    private String cityCode;

    @Column(name = "city_name",length = 128)
    private String cityName;

    @Column(name = "total_price",length = 15,scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "total_persons",length = 15)
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
