package com.relyme.linkOccupation.service.service_package.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务订单信息 视图
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_service_orders",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid,enterprise_uuid")
})
public class ServiceOrdersView extends BaseEntityForMysql {

    /**
     * 订单处理人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 服务套餐uuid
     */
    @Column(name = "service_package_uuid", length = 36)
    private String servicePackageUuid;

    /**
     * 套餐名称
     */
    @Column(name = "package_name",length = 128)
    private String packageName;

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;

    /**
     * 企业联系电话
     */
    @Column(name = "contact_phone",length = 12)
    private String contactPhone;

    /**
     * 企业联系人
     */
    @Column(name = "contact_person",length = 128)
    private String contactPerson;

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
     * 服务价格UUID
     */
    @Column(name = "service_prices_uuid",length = 36)
    private String servicePricesUuid;


    /**
     * 优惠活动UUID
     */
    @Column(name = "service_special_offer_uuid",length = 36)
    private String serviceSpecialOfferUuid;

    /**
     * 优惠活动类型 0 体验包 1 充值好礼
     */
    @Column(name = "special_type", length = 3,columnDefinition="int default 0")
    private Integer specialType;

    /**
     * 折扣 %
     */
    @Column(name = "service_discounts", scale = 2,length = 18)
    private BigDecimal serviceDiscounts;

    /**
     * 优惠月数
     */
    @Column(name = "special_monthes", length = 3,columnDefinition="int default 0")
    private Integer specialMonthes;

    /**
     * 优惠次数
     */
    @Column(name = "special_counts", length = 3,columnDefinition="int default 0")
    private Integer specialCounts;

    /**
     * 购买年数
     */
    @Column(name = "buy_years", length = 3,columnDefinition="int default 0")
    private Integer buyYears;

    /**
     * 赠送月数
     */
    @Column(name = "free_monthes", length = 3,columnDefinition="int default 0")
    private Integer freeMonthes;

    /**
     * 购买类型 1 按月购买 2按年购买
     */
    @Column(name = "buy_type", length = 3,columnDefinition="tinyint default 1")
    private Integer buyType;

    /**
     * 购买数量
     */
    @Column(name = "buy_num", length = 11,columnDefinition="int default 0")
    private Integer buNum;


    /**
     * 是否开发票 0 否 1是
     */
    @Column(name = "is_invoice", length = 3,columnDefinition="tinyint default 0")
    private Integer isInvoice;

    /**
     * 服务套餐订单编号
     */
    @Column(name = "service_package_order", length = 128)
    private String servicePackageOrder;

    /**
     * 购买金额
     */
    @Column(name = "buy_money",length = 18,scale = 2)
    private BigDecimal buyMoney;

    /**
     * 后台折扣
     */
    @Column(name = "background_discounts",length = 18,scale = 2)
    private BigDecimal backgroundDiscounts;

    /**
     * 实际购买金额
     */
    @Column(name = "true_buy_money",length = 18,scale = 2)
    private BigDecimal trueBuyMoney;

    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 支付时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 是否线下购买 0 否 1是
     */
    @Column(name = "is_buy_offline", length = 3,columnDefinition="tinyint default 0")
    private Integer isBuyOffline;

    /**
     * 是否已到期  0 否 1是
     */
    @Column(name = "is_expire", length = 3,columnDefinition="tinyint default 0")
    private Integer isExpire;



    /**
     * 企业规模 从订单价格来
     */
    @Transient
    private String enterpriseScale;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    public String getServicePricesUuid() {
        return servicePricesUuid;
    }

    public void setServicePricesUuid(String servicePricesUuid) {
        this.servicePricesUuid = servicePricesUuid;
    }

    public String getServiceSpecialOfferUuid() {
        return serviceSpecialOfferUuid;
    }

    public void setServiceSpecialOfferUuid(String serviceSpecialOfferUuid) {
        this.serviceSpecialOfferUuid = serviceSpecialOfferUuid;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }

    public BigDecimal getServiceDiscounts() {
        return serviceDiscounts;
    }

    public void setServiceDiscounts(BigDecimal serviceDiscounts) {
        this.serviceDiscounts = serviceDiscounts;
    }

    public Integer getSpecialMonthes() {
        return specialMonthes;
    }

    public void setSpecialMonthes(Integer specialMonthes) {
        this.specialMonthes = specialMonthes;
    }

    public Integer getSpecialCounts() {
        return specialCounts;
    }

    public void setSpecialCounts(Integer specialCounts) {
        this.specialCounts = specialCounts;
    }

    public Integer getBuyYears() {
        return buyYears;
    }

    public void setBuyYears(Integer buyYears) {
        this.buyYears = buyYears;
    }

    public Integer getFreeMonthes() {
        return freeMonthes;
    }

    public void setFreeMonthes(Integer freeMonthes) {
        this.freeMonthes = freeMonthes;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getBuNum() {
        return buNum;
    }

    public void setBuNum(Integer buNum) {
        this.buNum = buNum;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getServicePackageOrder() {
        return servicePackageOrder;
    }

    public void setServicePackageOrder(String servicePackageOrder) {
        this.servicePackageOrder = servicePackageOrder;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal buyMoney) {
        this.buyMoney = buyMoney;
    }

    public BigDecimal getBackgroundDiscounts() {
        return backgroundDiscounts;
    }

    public void setBackgroundDiscounts(BigDecimal backgroundDiscounts) {
        this.backgroundDiscounts = backgroundDiscounts;
    }

    public BigDecimal getTrueBuyMoney() {
        return trueBuyMoney;
    }

    public void setTrueBuyMoney(BigDecimal trueBuyMoney) {
        this.trueBuyMoney = trueBuyMoney;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getIsBuyOffline() {
        return isBuyOffline;
    }

    public void setIsBuyOffline(Integer isBuyOffline) {
        this.isBuyOffline = isBuyOffline;
    }

    public Integer getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Integer isExpire) {
        this.isExpire = isExpire;
    }

    public String getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(String enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }
}
