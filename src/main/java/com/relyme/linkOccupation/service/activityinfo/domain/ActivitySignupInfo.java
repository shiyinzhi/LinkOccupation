package com.relyme.linkOccupation.service.activityinfo.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 活动报名信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "activity_signup_info",indexes = {
        @Index(columnList = "uuid,name")
})
public class ActivitySignupInfo extends BaseEntityForMysql {


    /**
     * 姓名
     */
    @Column(name = "name",length = 50)
    private String name;

    /**
     * 电话
     */
    @Column(name = "mobile")
    private String mobile;


    /**
     * 活动名称
     */
    @Column(name = "activity_name",length = 150)
    private String activityName;

    /**
     * 活动唯一编号
     */
    @Column(name = "activity_uuid",length = 36)
    private String activityUuid;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    @Transient
    private ActivityInfo activityInfo;

    /**
     * 微信openid
     */
    @Column(name = "openid",length = 128)
    private String openid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActivityUuid() {
        return activityUuid;
    }

    public void setActivityUuid(String activityUuid) {
        this.activityUuid = activityUuid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
