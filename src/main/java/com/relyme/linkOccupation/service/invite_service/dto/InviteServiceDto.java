package com.relyme.linkOccupation.service.invite_service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 招聘服务InviteServiceDto
 * @author shiyinzhi
 */
@ApiModel(value = "招聘服务InviteServiceDto", description = "招聘服务InviteServiceDto")
public class InviteServiceDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新是使用")
    private String uuid;


    /**
     * 企业UUID  被代理的企业
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 部门UUID 被代理的企业 部门
     */
    @ApiModelProperty("部门UUID")
    private String departmentUuid;

    /**
     * 岗位UUID 被代理的企业 岗位UUID
     */
    @ApiModelProperty("部门 岗位")
    private String postUuid;

    /**
     * 招聘时间
     */
    @ApiModelProperty("招聘时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date inviteTime;

    /**
     * 招聘人数
     */
    @ApiModelProperty("招聘人数")
    private int inviteNums;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
