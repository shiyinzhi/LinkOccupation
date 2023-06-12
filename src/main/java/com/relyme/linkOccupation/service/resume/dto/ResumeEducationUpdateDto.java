package com.relyme.linkOccupation.service.resume.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 个人简历教育信息ResumeEducationUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历教育信息ResumeEducationUpdateDto", description = "个人简历教育信息ResumeEducationUpdateDto")
public class ResumeEducationUpdateDto {

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
     * 学历时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty("学历时间 yyyy-MM-dd")
    private Date educationTime;

    /**
     * 学校
     */
    @ApiModelProperty("学校")
    private String schoolName;

    /**
     * 担任职务描述
     */
    @ApiModelProperty("担任职务描述")
    private String fillAPost;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
