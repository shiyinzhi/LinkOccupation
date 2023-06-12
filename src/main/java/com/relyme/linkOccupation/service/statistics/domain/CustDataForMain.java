package com.relyme.linkOccupation.service.statistics.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 首页数据配置
 * @author shiyinzhi
 */
@Entity
@Table(name = "cust_data_for_main",indexes = {
        @Index(columnList = "uuid")
})
public class CustDataForMain extends BaseEntityForMysql {

    /**
     * 撮合总人数
     */
    @Column(name = "total_users",length = 15)
    int totalUsers;

    /**
     * 撮合总金额
     */
    @Column(name = "total_price",length = 15,scale = 2)
    BigDecimal totalPrice;


    /**
     * 总企业数
     */
    @Column(name = "total_enterprise",length = 15)
    int totalEnterprise;


    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalEnterprise() {
        return totalEnterprise;
    }

    public void setTotalEnterprise(int totalEnterprise) {
        this.totalEnterprise = totalEnterprise;
    }
}
