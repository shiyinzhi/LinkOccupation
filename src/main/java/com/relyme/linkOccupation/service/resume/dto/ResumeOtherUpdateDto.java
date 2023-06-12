package com.relyme.linkOccupation.service.resume.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * 个人简历其他信息ResumeOtherUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历其他信息ResumeOtherUpdateDto", description = "个人简历其他信息ResumeOtherUpdateDto")
public class ResumeOtherUpdateDto {

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
     * 重大疾病史
     */
    @ApiModelProperty("重大疾病史")
    private String majorDiseases;

    /**
     * 家族病史
     */
    @ApiModelProperty("家族病史")
    private String familyMedical;

    /**
     * 犯罪记录
     */
    @ApiModelProperty("犯罪记录")
    private String criminalRecord;

    /**
     * 职业要求
     */
    @ApiModelProperty("职业要求")
    private String careerDemand;

    /**
     * 期望薪资
     */
    @Column(name = "expectation_price",length = 18,scale = 2)
    private BigDecimal expectationPrice;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajorDiseases() {
        return majorDiseases;
    }

    public void setMajorDiseases(String majorDiseases) {
        this.majorDiseases = majorDiseases;
    }

    public String getFamilyMedical() {
        return familyMedical;
    }

    public void setFamilyMedical(String familyMedical) {
        this.familyMedical = familyMedical;
    }

    public String getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(String criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public String getCareerDemand() {
        return careerDemand;
    }

    public void setCareerDemand(String careerDemand) {
        this.careerDemand = careerDemand;
    }

    public BigDecimal getExpectationPrice() {
        return expectationPrice;
    }

    public void setExpectationPrice(BigDecimal expectationPrice) {
        this.expectationPrice = expectationPrice;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
