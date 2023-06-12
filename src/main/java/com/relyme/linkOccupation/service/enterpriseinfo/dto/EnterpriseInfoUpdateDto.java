package com.relyme.linkOccupation.service.enterpriseinfo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业雇主信息EnterpriseInfoUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "企业雇主信息EnterpriseInfoUpdateDto", description = "企业雇主信息EnterpriseInfoUpdateDto")
public class EnterpriseInfoUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;


    /**
     * 企业名称
     */
    @ApiModelProperty("企业名称")
    private String enterpriseName;


    /**
     * 企业简称
     */
    @ApiModelProperty("企业简称")
    private String enterpriseShortName;


    /**
     * 区划uuid
     */
    @ApiModelProperty("区划uuid")
    private String regionCodeUuid;

    /**
     * 所在地址
     */
    @ApiModelProperty("所在地址")
    private String address;

    /**
     * 企业营业执照图片名称带后缀
     */
    @ApiModelProperty("企业营业执照图片名称带后缀，上传后返回")
    private String businessLicensePic;


    /**
     * 法人姓名
     */
    @ApiModelProperty("法人姓名")
    private String legalPerson;

    /**
     * 企业联系电话
     */
    @ApiModelProperty("企业联系电话")
    private String contactPhone;

    /**
     * 企业联系人
     */
    @ApiModelProperty("企业联系人")
    private String contactPerson;

    /**
     * 企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企
     */
    @ApiModelProperty("企业类型 1个体商户 2国有企业 3私有企业 4国有控股企业 5外企")
    private int enterpriseType;

    /**
     * 提交人/确认人 关联客户账户
     */
    @ApiModelProperty("提交人/确认人 关联客户账户")
    private String custAccountUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(int enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
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
}
