package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigDecimal;

/**
 * 后台首页统计
 */
public class BackMainPageStatistics extends BaseEntityForMysql {

    /**
     * 撮合总人数
     */
    int totalUsers;

    /**
     * 撮合总金额
     */
    BigDecimal totalPrice;


    /**
     * 总企业数
     */
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
