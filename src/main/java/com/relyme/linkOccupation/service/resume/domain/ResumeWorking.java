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
 * 个人简历工作经历信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_working",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeWorking extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    /**
     * 工作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "working_time")
    private Date workingTime;

    /**
     * 公司名称
     */
    @Column(name = "company_name",length = 128)
    private String companyName;

    /**
     * 担任职务
     */
    @Column(name = "fill_a_post",length = 256)
    private String fillAPost;

    /**
     * 职务描述
     */
    @Column(name = "post_desc",length = 256)
    private String postDesc;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public Date getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Date workingTime) {
        this.workingTime = workingTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getFillAPost() {
        return fillAPost;
    }

    public void setFillAPost(String fillAPost) {
        this.fillAPost = fillAPost;
    }
}
