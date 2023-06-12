package com.relyme.linkOccupation.service.resume.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 个人简历家庭信息ResumeFamilyUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历家庭信息ResumeFamilyUpdateDto", description = "个人简历家庭信息ResumeFamilyUpdateDto")
public class ResumeFamilyUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 创建人uuid
     */
    @ApiModelProperty("创建人uuid")
    private String custAccountUuid;


    /**
     * 成员名称
     */
    @ApiModelProperty("成员名称")
    private String memberName;

    /**
     * 关系
     */
    @ApiModelProperty("关系")
    private String memberRelation;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /**
     * 收入
     */
    @ApiModelProperty("收入")
    private BigDecimal memberEarning;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRelation() {
        return memberRelation;
    }

    public void setMemberRelation(String memberRelation) {
        this.memberRelation = memberRelation;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public BigDecimal getMemberEarning() {
        return memberEarning;
    }

    public void setMemberEarning(BigDecimal memberEarning) {
        this.memberEarning = memberEarning;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
