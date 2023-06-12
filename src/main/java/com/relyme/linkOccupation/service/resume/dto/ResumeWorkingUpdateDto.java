package com.relyme.linkOccupation.service.resume.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 个人简历工作经历信息ResumeWorkingUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "个人简历工作经历信息ResumeWorkingUpdateDto", description = "个人简历工作经历信息ResumeWorkingUpdateDto")
public class ResumeWorkingUpdateDto {

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
     * 工作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty("工作时间 yyyy-MM-dd")
    private Date workingTime;

    /**
     * 公司名称
     */
    @ApiModelProperty("公司名称")
    private String companyName;

    /**
     * 担任职务
     */
    @ApiModelProperty("担任职务")
    private String fillAPost;

    /**
     * 职务描述
     */
    @ApiModelProperty("职务描述")
    private String postDesc;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getFillAPost() {
        return fillAPost;
    }

    public void setFillAPost(String fillAPost) {
        this.fillAPost = fillAPost;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
