package com.relyme.linkOccupation.service.service_package.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 服务价格信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "service_prices",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid")
})
public class ServicePrices extends BaseEntityForMysql {

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
     * 企业人数下限
     */
    @Column(name = "employees_lower_limit", length = 3,columnDefinition="int default 0")
    private int employeesLowerLimit;

    /**
     * 企业人数上限
     */
    @Column(name = "employees_upper_limit", length = 3,columnDefinition="int default 0")
    private int employeesUpperLimit;

    /**
     * 月价格
     */
    @Column(name = "month_price", scale = 2,length = 18)
    private BigDecimal monthPrice;

    /**
     * 年价格
     */
    @Column(name = "year_price", scale = 2,length = 18)
    private BigDecimal yearPrice;

    @Transient
    private String postNum;

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

    public int getEmployeesLowerLimit() {
        return employeesLowerLimit;
    }

    public void setEmployeesLowerLimit(int employeesLowerLimit) {
        this.employeesLowerLimit = employeesLowerLimit;
    }

    public int getEmployeesUpperLimit() {
        return employeesUpperLimit;
    }

    public void setEmployeesUpperLimit(int employeesUpperLimit) {
        this.employeesUpperLimit = employeesUpperLimit;
    }

    public BigDecimal getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(BigDecimal monthPrice) {
        this.monthPrice = monthPrice;
    }

    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }
}
