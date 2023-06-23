package com.relyme.linkOccupation.service.service_package.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务订单信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "service_orders",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid,enterprise_uuid")
})
public class ServiceOrders extends BaseEntityForMysql {

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
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

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
     * 购买类型 1 按月购买 2按年购买
     */
    @Column(name = "buy_type", length = 3,columnDefinition="tinyint default 1")
    private int buyType;

    /**
     * 购买数量
     */
    @Column(name = "buy_num", length = 11,columnDefinition="int default 0")
    private int buyNum;


    /**
     * 是否开发票 0 否 1是
     */
    @Column(name = "is_invoice", length = 3,columnDefinition="tinyint default 0")
    private int isInvoice;

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
     * 是否线下购买 -1待处理 0 否 1是
     */
    @Column(name = "is_buy_offline", length = 3,columnDefinition="tinyint default -1")
    private int isBuyOffline;

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

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getServiceSpecialOfferUuid() {
        return serviceSpecialOfferUuid;
    }

    public void setServiceSpecialOfferUuid(String serviceSpecialOfferUuid) {
        this.serviceSpecialOfferUuid = serviceSpecialOfferUuid;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public int getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(int isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getServicePricesUuid() {
        return servicePricesUuid;
    }

    public void setServicePricesUuid(String servicePricesUuid) {
        this.servicePricesUuid = servicePricesUuid;
    }

    public String getServicePackageOrder() {
        return servicePackageOrder;
    }

    public void setServicePackageOrder(String servicePackageOrder) {
        this.servicePackageOrder = servicePackageOrder;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal buyMoney) {
        this.buyMoney = buyMoney;
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

    public int getIsBuyOffline() {
        return isBuyOffline;
    }

    public void setIsBuyOffline(int isBuyOffline) {
        this.isBuyOffline = isBuyOffline;
    }

    public String getEnterpriseScale() {
        return enterpriseScale;
    }

    public void setEnterpriseScale(String enterpriseScale) {
        this.enterpriseScale = enterpriseScale;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
