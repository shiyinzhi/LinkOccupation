package com.relyme.linkOccupation.service.legal_advice.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 法律咨询信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "legal_advice",indexes = {
        @Index(columnList = "uuid,user_account_uuid,cust_account_uuid,enterprise_uuid")
})
public class LegalAdvice extends BaseEntityForMysql {


    /**
     * 处理人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 用户uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 法律咨询内容
     */
    @Column(name = "legal_content",length = 512,nullable = false)
    private String legalContent;

    /**
     * 处理状态
     * -1暂不处理
     * 0待处理：管理员未处理，电话沟通二次确认，操作暂不处理，则状态变为已处理
     * 1处理中：已打电话联系，但用户还未点击满意
     * 2已处理：用户已评价
     */
    @Column(name = "handle_status", length = 3,columnDefinition="tinyint default 0")
    private int handleStatus;

    /**
     * 处理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "handle_time")
    private Date handleTime;


    /**
     * 是否超过24小时 0未超过 1超过
     */
    @Column(name = "exp_hour_twofour", length = 3,columnDefinition="tinyint default 0")
    private int expHourTwofour;

    /**
     * 是否满意  0不满意 1满意
     */
    @Column(name = "handle_satisfied", length = 3,columnDefinition="tinyint default 0")
    private int handleSatisfied;

    /**
     * 评价时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "handle_satisfied_time")
    private Date handleSatisfiedTime;

    /**
     * 处理时长 小时
     */
    @Column(name = "handle_hours", length = 11,scale = 2,columnDefinition="double default 0")
    private double handleHours;


    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getLegalContent() {
        return legalContent;
    }

    public void setLegalContent(String legalContent) {
        this.legalContent = legalContent;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public int getExpHourTwofour() {
        return expHourTwofour;
    }

    public void setExpHourTwofour(int expHourTwofour) {
        this.expHourTwofour = expHourTwofour;
    }

    public int getHandleSatisfied() {
        return handleSatisfied;
    }

    public void setHandleSatisfied(int handleSatisfied) {
        this.handleSatisfied = handleSatisfied;
    }

    public double getHandleHours() {
        return handleHours;
    }

    public void setHandleHours(double handleHours) {
        this.handleHours = handleHours;
    }

    public Date getHandleSatisfiedTime() {
        return handleSatisfiedTime;
    }

    public void setHandleSatisfiedTime(Date handleSatisfiedTime) {
        this.handleSatisfiedTime = handleSatisfiedTime;
    }
}
