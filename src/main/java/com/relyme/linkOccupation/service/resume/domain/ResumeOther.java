package com.relyme.linkOccupation.service.resume.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 个人简历其他信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_other",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeOther extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 重大疾病史
     */
    @Column(name = "major_diseases",length = 256)
    private String majorDiseases;

    /**
     * 家族病史
     */
    @Column(name = "family_medical",length = 256)
    private String familyMedical;

    /**
     * 犯罪记录
     */
    @Column(name = "criminal_record",length = 256)
    private String criminalRecord;

    /**
     * 职业要求
     */
    @Column(name = "career_demand",length = 256)
    private String careerDemand;

    /**
     * 期望薪资
     */
    @Column(name = "expectation_price",length = 18,scale = 2)
    private BigDecimal expectationPrice;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
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
}
