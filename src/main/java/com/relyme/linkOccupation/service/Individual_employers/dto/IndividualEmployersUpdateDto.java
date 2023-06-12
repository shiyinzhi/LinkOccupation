package com.relyme.linkOccupation.service.Individual_employers.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 个人雇主信息IndividualEmployersUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人雇主信息IndividualEmployersUpdateDto", description = "个人雇主信息IndividualEmployersUpdateDto")
public class IndividualEmployersUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;


    /**
     * 个人名称
     */
    @ApiModelProperty("个人名称")
    private String individualName;

    /**
     * 个人名称 简称
     */
    @ApiModelProperty("个人简称")
    private String individualShortName;

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
     * 正面身份证图片名称带后缀
     */
    @ApiModelProperty("正面身份证图片名称带后缀")
    private String frontIdcardPic;

    /**
     * 背面身份证图片名称带后缀
     */
    @ApiModelProperty("背面身份证图片名称带后缀")
    private String backIdcardPic;


    /**
     * 提交人/确认人 关联客户账户
     */
    @ApiModelProperty("关联客户账户")
    private String custAccountUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getRegionCodeUuid() {
        return regionCodeUuid;
    }

    public void setRegionCodeUuid(String regionCodeUuid) {
        this.regionCodeUuid = regionCodeUuid;
    }

    public String getIndividualShortName() {
        return individualShortName;
    }

    public void setIndividualShortName(String individualShortName) {
        this.individualShortName = individualShortName;
    }
}
