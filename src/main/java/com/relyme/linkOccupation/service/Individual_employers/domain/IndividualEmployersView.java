package com.relyme.linkOccupation.service.Individual_employers.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 个人雇主信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_individual_employers",indexes = {
        @Index(columnList = "uuid,individual_name")
})
public class IndividualEmployersView extends BaseEntityForMysql {


    /**
     * 个人名称
     */
    @Column(name = "individual_name",length = 150)
    private String individualName;

    /**
     * 所在地址
     */
    @Column(name = "address",length = 128)
    private String address;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @Column(name = "enterprise_type", length = 3,columnDefinition="tinyint default 1")
    private int enterpriseType;

    /**
     * 正面身份证图片名称带后缀
     */
    @Column(name = "front_idcard_pic",length = 128)
    private String frontIdcardPic;

    /**
     * 背面身份证图片名称带后缀
     */
    @Column(name = "back_idcard_pic",length = 128)
    private String backIdcardPic;


    /**
     * 提交人/确认人 关联客户账户
     */
    @Column(name = "cust_account_uuid",length = 36)
    private String custAccountUuid;

    /**
     * 信用分
     */
    @Column(name = "credit_Score",length = 11,scale = 2)
    private BigDecimal creditScore;


    /**
     * 是否进入黑名单 0 未进入 1进入
     */
    @Column(name = "is_in_blacklist", length = 3,columnDefinition="tinyint default 0")
    private int isInBlacklist;

    /**
     * 是否审核 0 未审核 1审核通过 2审核不通过
     */
    @Column(name = "is_audit", length = 3,columnDefinition="tinyint default 0")
    private int isAudit;


    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;


    /**
     * 昵称
     */
    @Column(name = "nickname",length = 128)
    private String nickname;


    /**
     * 头像名称包括后缀
     */
    @Column(name = "picture_url",length = 512)
    private String pictureURL;

    /**
     * 推荐人uuid
     */
    @Column(name = "referrer_uuid",length = 36)
    private String referrerUuid;


    @Transient
    private String frontIdcardPicPath;

    @Transient
    private String backIdcardPicPath;

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getFrontIdcardPic() {
        return frontIdcardPic;
    }

    public void setFrontIdcardPic(String frontIdcardPic) {
        this.frontIdcardPic = frontIdcardPic;
    }

    public String getBackIdcardPic() {
        return backIdcardPic;
    }

    public void setBackIdcardPic(String backIdcardPic) {
        this.backIdcardPic = backIdcardPic;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public int getIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(int isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public String getFrontIdcardPicPath() {
        return frontIdcardPicPath;
    }

    public void setFrontIdcardPicPath(String frontIdcardPicPath) {
        this.frontIdcardPicPath = frontIdcardPicPath;
    }

    public String getBackIdcardPicPath() {
        return backIdcardPicPath;
    }

    public void setBackIdcardPicPath(String backIdcardPicPath) {
        this.backIdcardPicPath = backIdcardPicPath;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getReferrerUuid() {
        return referrerUuid;
    }

    public void setReferrerUuid(String referrerUuid) {
        this.referrerUuid = referrerUuid;
    }
}
