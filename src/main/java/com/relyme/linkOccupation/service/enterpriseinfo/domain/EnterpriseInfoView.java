package com.relyme.linkOccupation.service.enterpriseinfo.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 企业信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_enterprise_info",indexes = {
        @Index(columnList = "uuid,enterprise_name")
})
public class EnterpriseInfoView extends BaseEntityForMysql {

    /**
     * 企业名称
     */
    @Column(name = "enterprise_name",length = 150)
    private String enterpriseName;


    /**
     * 企业名称
     */
    @Column(name = "enterprise_short_name",length = 150)
    private String enterpriseShortName;


    /**
     * 区划uuid
     */
    @Column(name = "region_code_uuid",length = 36)
    private String regionCodeUuid;

    /**
     * 所在地址
     */
    @Column(name = "address",length = 128)
    private String address;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @Column(name = "enterprise_type", length = 3,columnDefinition="tinyint default 0")
    private int enterpriseType;

    /**
     * 企业营业执照图片名称带后缀
     */
    @Column(name = "business_license_pic",length = 128)
    private String businessLicensePic;


    /**
     * 法人姓名
     */
    @Column(name = "legal_person",length = 128)
    private String legalPerson;

    /**
     * 企业联系电话
     */
    @Column(name = "contact_phone",length = 512)
    private String contactPhone;

    /**
     * 企业联系人
     */
    @Column(name = "contact_person",length = 128)
    private String contactPerson;


    /**
     * 提交人/确认人 关联客户账户
     */
    @Column(name = "cust_account_uuid",length = 36)
    private String custAccountUuid;

    /**
     * 信用分 默认为100分
     */
    @Column(name = "credit_Score",length = 11,scale = 2)
    private BigDecimal creditScore = new BigDecimal(100);


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
     * 推荐人uuid
     */
    @Column(name = "referrer_uuid",length = 36)
    private String referrerUuid;


    @Transient
    private String businessLicensePath;


    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessLicensePic() {
        return businessLicensePic;
    }

    public void setBusinessLicensePic(String businessLicensePic) {
        this.businessLicensePic = businessLicensePic;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
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

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public BigDecimal getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(BigDecimal creditScore) {
        this.creditScore = creditScore;
    }

    public String getBusinessLicensePath() {
        return businessLicensePath;
    }

    public void setBusinessLicensePath(String businessLicensePath) {
        this.businessLicensePath = businessLicensePath;
    }

    public int getIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(int isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public String getRegionCodeUuid() {
        return regionCodeUuid;
    }

    public void setRegionCodeUuid(String regionCodeUuid) {
        this.regionCodeUuid = regionCodeUuid;
    }

    public String getEnterpriseShortName() {
        return enterpriseShortName;
    }

    public void setEnterpriseShortName(String enterpriseShortName) {
        this.enterpriseShortName = enterpriseShortName;
    }

    public String getReferrerUuid() {
        return referrerUuid;
    }

    public void setReferrerUuid(String referrerUuid) {
        this.referrerUuid = referrerUuid;
    }
}
