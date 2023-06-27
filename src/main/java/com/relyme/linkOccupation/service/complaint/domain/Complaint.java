package com.relyme.linkOccupation.service.complaint.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 投诉信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "complaint",indexes = {
        @Index(columnList = "uuid,user_account_uuid,cust_account_uuid,enterprise_uuid")
})
public class Complaint extends BaseEntityForMysql {


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
     * 投诉内容
     */
    @Column(name = "complaint_content",length = 512,nullable = false)
    private String complaintContent;

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
     * 是否满意  0 待评价 1不满意 2满意
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

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 姓名
     */
    @Column(name = "name",length = 128)
    private String name;


    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
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

    public int getHandleSatisfied() {
        return handleSatisfied;
    }

    public void setHandleSatisfied(int handleSatisfied) {
        this.handleSatisfied = handleSatisfied;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getHandleSatisfiedTime() {
        return handleSatisfiedTime;
    }

    public void setHandleSatisfiedTime(Date handleSatisfiedTime) {
        this.handleSatisfiedTime = handleSatisfiedTime;
    }

    public double getHandleHours() {
        return handleHours;
    }

    public void setHandleHours(double handleHours) {
        this.handleHours = handleHours;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
