package com.relyme.linkOccupation.service.resume.dto;


import com.relyme.linkOccupation.service.resume.domain.*;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 简历信息ResumeOutDto
 * @author shiyinzhi
 */
@ApiModel(value = "简历信息ResumeOutDto", description = "简历信息ResumeOutDto")
public class ResumeOutDto extends BaseEntityForMysql {


    @ApiModelProperty("个人简历基本信息")
    private ResumeBase resumeBase;

    @ApiModelProperty("个人简历任务期望信息")
    private List<ResumeExpectation> resumeExpectationList;

    @ApiModelProperty("个人简历学习经历信息")
    private List<ResumeEducation> resumeEducationList;

    @ApiModelProperty("个人简历工作经历信息")
    private List<ResumeWorking> resumeWorkingList;

    @ApiModelProperty("个人简历家庭情况信息")
    private List<ResumeFamily> resumeFamilyList;

    @ApiModelProperty("个人简历其他信息")
    private ResumeOther resumeOther;

    public ResumeBase getResumeBase() {
        return resumeBase;
    }

    public void setResumeBase(ResumeBase resumeBase) {
        this.resumeBase = resumeBase;
    }

    public List<ResumeExpectation> getResumeExpectationList() {
        return resumeExpectationList;
    }

    public void setResumeExpectationList(List<ResumeExpectation> resumeExpectationList) {
        this.resumeExpectationList = resumeExpectationList;
    }

    public List<ResumeEducation> getResumeEducationList() {
        return resumeEducationList;
    }

    public void setResumeEducationList(List<ResumeEducation> resumeEducationList) {
        this.resumeEducationList = resumeEducationList;
    }

    public List<ResumeWorking> getResumeWorkingList() {
        return resumeWorkingList;
    }

    public void setResumeWorkingList(List<ResumeWorking> resumeWorkingList) {
        this.resumeWorkingList = resumeWorkingList;
    }

    public List<ResumeFamily> getResumeFamilyList() {
        return resumeFamilyList;
    }

    public void setResumeFamilyList(List<ResumeFamily> resumeFamilyList) {
        this.resumeFamilyList = resumeFamilyList;
    }

    public ResumeOther getResumeOther() {
        return resumeOther;
    }

    public void setResumeOther(ResumeOther resumeOther) {
        this.resumeOther = resumeOther;
    }
}
