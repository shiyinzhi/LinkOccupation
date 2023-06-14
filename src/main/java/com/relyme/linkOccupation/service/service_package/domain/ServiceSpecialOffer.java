package com.relyme.linkOccupation.service.service_package.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 服务优惠活动信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "service_special_offer",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid")
})
public class ServiceSpecialOffer extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 服务套餐uuid
     */
    @Column(name = "service_package_uuid", length = 36)
    private String servicePackageUuid;

    /**
     * 优惠活动类型 0 体验包 1 充值好礼
     */
    @Column(name = "special_type", length = 3,columnDefinition="int default 0")
    private int specialType;

    /**
     * 折扣 %
     */
    @Column(name = "service_discounts", scale = 2,length = 18)
    private BigDecimal serviceDiscounts;

    /**
     * 优惠月数
     */
    @Column(name = "special_monthes", length = 3,columnDefinition="int default 0")
    private int specialMonthes;

    /**
     * 优惠次数
     */
    @Column(name = "special_counts", length = 3,columnDefinition="int default 0")
    private int specialCounts;

    /**
     * 购买年数
     */
    @Column(name = "buy_years", length = 3,columnDefinition="int default 0")
    private int buyYears;

    /**
     * 赠送月数
     */
    @Column(name = "free_monthes", length = 3,columnDefinition="int default 0")
    private int freeMonthes;

    /**
     * 是否下架 0上架 1下架
     */
    @Column(name = "is_close", length = 3,columnDefinition="tinyint default 0")
    private int isClose;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }

    public BigDecimal getServiceDiscounts() {
        return serviceDiscounts;
    }

    public void setServiceDiscounts(BigDecimal serviceDiscounts) {
        this.serviceDiscounts = serviceDiscounts;
    }

    public int getSpecialMonthes() {
        return specialMonthes;
    }

    public void setSpecialMonthes(int specialMonthes) {
        this.specialMonthes = specialMonthes;
    }

    public int getSpecialCounts() {
        return specialCounts;
    }

    public void setSpecialCounts(int specialCounts) {
        this.specialCounts = specialCounts;
    }

    public int getBuyYears() {
        return buyYears;
    }

    public void setBuyYears(int buyYears) {
        this.buyYears = buyYears;
    }

    public int getFreeMonthes() {
        return freeMonthes;
    }

    public void setFreeMonthes(int freeMonthes) {
        this.freeMonthes = freeMonthes;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }
}
