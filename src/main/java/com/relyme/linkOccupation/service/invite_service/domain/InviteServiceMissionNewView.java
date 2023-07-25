package com.relyme.linkOccupation.service.invite_service.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 招聘信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_mission_invite_service_new",indexes = {
        @Index(columnList = "uuid,enterprise_uuid")
})
public class InviteServiceMissionNewView extends BaseEntityForMysql {


    /**
     * 企业UUID  被代理的企业
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 部门UUID 被代理的企业 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    /**
     * 部门名称
     */
    @Column(name = "department_name",length = 150)
    private String departmentName;

    /**
     * 岗位UUID 被代理的企业 部门 岗位
     */
    @Column(name = "post_uuid",length = 36)
    private String postUuid;

    /**
     * 岗位名称
     */
    @Column(name = "post_name",length = 150)
    private String postName;

    /**
     * 招聘人数
     */
    @Column(name = "person_count", length = 11,columnDefinition="tinyint default 0")
    private int personCount;

    /**
     * 是否后台代发 0不是 1是
     */
    @Column(name = "is_agency_published", length = 3,columnDefinition="tinyint default 0")
    private int isAgencyPublished;

    @Transient
    private int countNum;

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getIsAgencyPublished() {
        return isAgencyPublished;
    }

    public void setIsAgencyPublished(int isAgencyPublished) {
        this.isAgencyPublished = isAgencyPublished;
    }
}
