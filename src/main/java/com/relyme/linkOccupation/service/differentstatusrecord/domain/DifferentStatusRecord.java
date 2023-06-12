package com.relyme.linkOccupation.service.differentstatusrecord.domain;


import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 花名册人员异动记录信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "different_status_record",indexes = {
        @Index(columnList = "uuid,job_number,roster_uuid")
})
public class DifferentStatusRecord extends BaseEntityForMysql {

    /**
     * 工号
     * 工号：QYJC-01-000001
     * 含义：企业简称-岗位性质-工号员工
     * 岗位性质：01表示全职人员、02表示退休返聘人员、03表示兼职和临时工等、04表示其他类
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;

    /**
     * 新工号
     */
    @Column(name = "job_number_new",length = 128)
    private String jobNumberNew;


    /**
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    /**
     * 新部门
     */
    @Column(name = "department_uuid_new",length = 36)
    private String departmentUuidNew;


    @Transient
    private String  departmentName;

    @Transient
    private String  departmentNameNew;

    /**
     * 工作岗位
     */
    @Column(name = "post_uuid",length = 36)
    private String postUuid;

    /**
     * 新工作岗位
     */
    @Column(name = "post_uuid_new",length = 36)
    private String postUuidNew;

    /**
     * 工作岗位 名称
     */
    @Transient
    private String postName;

    /**
     * 新工作岗位 名称
     */
    @Transient
    private String postNameNew;

    /**
     * 职工类别
     */
    @Column(name = "category_uuid",length = 36)
    private String categoryUuid;

    /**
     * 职工类别
     */
    @Column(name = "category_uuid_new",length = 36)
    private String categoryUuidNew;

    /**
     * 职工类别 名称
     */
    @Transient
    private String categoryName;

    /**
     * 新职工类别 名称
     */
    @Transient
    private String categoryNameNew;


    /**
     * 异动状态
     */
    @Column(name = "different_status_uuid",length = 36)
    private String differentStatusUuid;

    /**
     * 异动状态 名称
     */
    @Transient
    private String differentStatusName;


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
     * 花名册uuid
     */
    @Column(name = "roster_uuid",length = 36)
    private String rosterUuid;

    @Transient
    private Roster roster;


    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getRosterUuid() {
        return rosterUuid;
    }

    public void setRosterUuid(String rosterUuid) {
        this.rosterUuid = rosterUuid;
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getDifferentStatusUuid() {
        return differentStatusUuid;
    }

    public void setDifferentStatusUuid(String differentStatusUuid) {
        this.differentStatusUuid = differentStatusUuid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
    }

    public String getJobNumberNew() {
        return jobNumberNew;
    }

    public void setJobNumberNew(String jobNumberNew) {
        this.jobNumberNew = jobNumberNew;
    }

    public String getDepartmentUuidNew() {
        return departmentUuidNew;
    }

    public void setDepartmentUuidNew(String departmentUuidNew) {
        this.departmentUuidNew = departmentUuidNew;
    }

    public String getDepartmentNameNew() {
        return departmentNameNew;
    }

    public void setDepartmentNameNew(String departmentNameNew) {
        this.departmentNameNew = departmentNameNew;
    }

    public String getPostUuidNew() {
        return postUuidNew;
    }

    public void setPostUuidNew(String postUuidNew) {
        this.postUuidNew = postUuidNew;
    }

    public String getPostNameNew() {
        return postNameNew;
    }

    public void setPostNameNew(String postNameNew) {
        this.postNameNew = postNameNew;
    }

    public String getCategoryUuidNew() {
        return categoryUuidNew;
    }

    public void setCategoryUuidNew(String categoryUuidNew) {
        this.categoryUuidNew = categoryUuidNew;
    }

    public String getCategoryNameNew() {
        return categoryNameNew;
    }

    public void setCategoryNameNew(String categoryNameNew) {
        this.categoryNameNew = categoryNameNew;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
