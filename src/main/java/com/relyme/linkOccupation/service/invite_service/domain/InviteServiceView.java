package com.relyme.linkOccupation.service.invite_service.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 招聘信息视图
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_invite_service",indexes = {
        @Index(columnList = "uuid,user_account_uuid,enterprise_uuid")
})
public class InviteServiceView extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;


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
     * 招聘时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "invite_time")
    private Date inviteTime;

    /**
     * 招聘人数
     */
    @Column(name = "invite_nums", length = 3,columnDefinition="int default 0")
    private int inviteNums;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

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

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public int getInviteNums() {
        return inviteNums;
    }

    public void setInviteNums(int inviteNums) {
        this.inviteNums = inviteNums;
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
}
