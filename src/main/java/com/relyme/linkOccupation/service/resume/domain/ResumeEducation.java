package com.relyme.linkOccupation.service.resume.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 个人简历学历经理信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_education",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeEducation extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 学历时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "education_time")
    private Date educationTime;

    /**
     * 学校
     */
    @Column(name = "school_name",length = 128)
    private String schoolName;

    /**
     * 担任职务描述
     */
    @Column(name = "fill_a_post",length = 256)
    private String fillAPost;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public Date getEducationTime() {
        return educationTime;
    }

    public void setEducationTime(Date educationTime) {
        this.educationTime = educationTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getFillAPost() {
        return fillAPost;
    }

    public void setFillAPost(String fillAPost) {
        this.fillAPost = fillAPost;
    }
}
