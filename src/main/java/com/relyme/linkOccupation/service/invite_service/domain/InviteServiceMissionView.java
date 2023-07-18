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
 * 招聘信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_mission_invite_service",indexes = {
        @Index(columnList = "uuid,enterprise_uuid")
})
public class InviteServiceMissionView extends BaseEntityForMysql {


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
     * 雇员端状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价
     */
    @Column(name = "mission_record_status", length = 3,columnDefinition="tinyint default 0")
    private int missionRecordStatus;

    /**
     * 招聘人数
     */
    @Column(name = "count_num", length = 3,columnDefinition="int default 0")
    private int countNum = 1;

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

    public int getMissionRecordStatus() {
        return missionRecordStatus;
    }

    public void setMissionRecordStatus(int missionRecordStatus) {
        this.missionRecordStatus = missionRecordStatus;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }
}
