package com.relyme.linkOccupation.service.recruitment.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 招聘信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "recruitment_info",indexes = {
        @Index(columnList = "uuid,recruitment_name")
})
public class RecruitmentInfo extends BaseEntityForMysql {


    /**
     * 日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "com_data")
    private Date comData;

    /**
     * 发生年月 2020年1月
     */
    @Column(name = "com_year_month",length = 128)
    private String comYearMonth;

    /**
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    @Transient
    private String  departmentName;

    /**
     * 工作岗位
     */
    @Column(name = "post_uuid",length = 36)
    private String postUuid;

    /**
     * 姓名
     */
    @Column(name = "recruitment_name",length = 128)
    private String recruitmentName;

    /**
     * 性别
     */
    @Column(name = "sex",length = 10)
    private String sex;


    /**
     * 年龄
     */
    @Column(name = "age",columnDefinition="tinyint default 0")
    private int age;

    /**
     * 学历
     */
    @Column(name = "education_uuid",length = 36)
    private String educationUuid;

    /**
     * 学历 名称
     */
    @Transient
    private String educationName;

    /**
     * 联系电话
     */
    @Column(name = "mobile",length = 12)
    private String mobile;


    /**
     * 简历来源
     */
    @Column(name = "resume_source_uuid",length = 36)
    private String resumeSourceUuid;

    /**
     * 简历来源 名称
     */
    @Transient
    private String resumeSourceName;


    /**
     * 获取方式
     */
    @Column(name = "get_type_uuid",length = 36)
    private String getTypeUuid;

    /**
     * 获取方式 名称
     */
    @Transient
    private String getTypeName;


    /**
     * 是否邀约 0否 1是 2其他
     */
    @Column(name = "is_invite",columnDefinition="tinyint default 0")
    private int isInvite;


    /**
     * 是否面试 0否 1是 2其他
     */
    @Column(name = "is_interview",columnDefinition="tinyint default 0")
    private int isInterview;


    /**
     * 初试时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "first_interview_time",updatable = false)
    private Date firstInterviewTime;

    /**
     * 是否复试 0否 1是 2其他
     */
    @Column(name = "is_reexamine",columnDefinition="tinyint default 0")
    private int isReexamine;

    /**
     * 复试时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "reexamine_time",updatable = false)
    private Date reexamineTime;


    /**
     * 是否终试 0否 1是 2其他
     */
    @Column(name = "is_final_interview",columnDefinition="tinyint default 0")
    private int isFinalInterview;

    /**
     * 终试时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "final_interview_time",updatable = false)
    private Date finalInterviewTime;

    /**
     * 是否确认入职 0否 1是 2其他
     */
    @Column(name = "is_shure_join",columnDefinition="tinyint default 0")
    private int isShureJoin;

    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "shure_join_time",updatable = false)
    private Date shureJoinTime;

    /**
     * 邀约纪要
     */
    @Column(name = "invite_remark")
    private String inviteRemark;

    /**
     * 证件号码 身份证号码
     */
    @Column(name = "identity_card_no",length = 20)
    private String identityCardNo;


    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 企业信息 名称
     */
    @Transient
    private String enterpriseName;

    /**
     * 提交人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    public Date getComData() {
        return comData;
    }

    public void setComData(Date comData) {
        this.comData = comData;
    }

    public String getComYearMonth() {
        return comYearMonth;
    }

    public void setComYearMonth(String comYearMonth) {
        this.comYearMonth = comYearMonth;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public String getRecruitmentName() {
        return recruitmentName;
    }

    public void setRecruitmentName(String recruitmentName) {
        this.recruitmentName = recruitmentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducationUuid() {
        return educationUuid;
    }

    public void setEducationUuid(String educationUuid) {
        this.educationUuid = educationUuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getResumeSourceUuid() {
        return resumeSourceUuid;
    }

    public void setResumeSourceUuid(String resumeSourceUuid) {
        this.resumeSourceUuid = resumeSourceUuid;
    }

    public String getGetTypeUuid() {
        return getTypeUuid;
    }

    public void setGetTypeUuid(String getTypeUuid) {
        this.getTypeUuid = getTypeUuid;
    }

    public int getIsInvite() {
        return isInvite;
    }

    public void setIsInvite(int isInvite) {
        this.isInvite = isInvite;
    }

    public int getIsInterview() {
        return isInterview;
    }

    public void setIsInterview(int isInterview) {
        this.isInterview = isInterview;
    }

    public Date getFirstInterviewTime() {
        return firstInterviewTime;
    }

    public void setFirstInterviewTime(Date firstInterviewTime) {
        this.firstInterviewTime = firstInterviewTime;
    }

    public int getIsReexamine() {
        return isReexamine;
    }

    public void setIsReexamine(int isReexamine) {
        this.isReexamine = isReexamine;
    }

    public Date getReexamineTime() {
        return reexamineTime;
    }

    public void setReexamineTime(Date reexamineTime) {
        this.reexamineTime = reexamineTime;
    }

    public int getIsFinalInterview() {
        return isFinalInterview;
    }

    public void setIsFinalInterview(int isFinalInterview) {
        this.isFinalInterview = isFinalInterview;
    }

    public Date getFinalInterviewTime() {
        return finalInterviewTime;
    }

    public void setFinalInterviewTime(Date finalInterviewTime) {
        this.finalInterviewTime = finalInterviewTime;
    }

    public int getIsShureJoin() {
        return isShureJoin;
    }

    public void setIsShureJoin(int isShureJoin) {
        this.isShureJoin = isShureJoin;
    }

    public Date getShureJoinTime() {
        return shureJoinTime;
    }

    public void setShureJoinTime(Date shureJoinTime) {
        this.shureJoinTime = shureJoinTime;
    }

    public String getInviteRemark() {
        return inviteRemark;
    }

    public void setInviteRemark(String inviteRemark) {
        this.inviteRemark = inviteRemark;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getResumeSourceName() {
        return resumeSourceName;
    }

    public void setResumeSourceName(String resumeSourceName) {
        this.resumeSourceName = resumeSourceName;
    }

    public String getGetTypeName() {
        return getTypeName;
    }

    public void setGetTypeName(String getTypeName) {
        this.getTypeName = getTypeName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
