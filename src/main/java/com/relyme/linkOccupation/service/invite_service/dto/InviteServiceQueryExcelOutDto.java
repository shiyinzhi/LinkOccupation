package com.relyme.linkOccupation.service.invite_service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 招聘服务InviteServiceQueryExcelOutDto
 * @author shiyinzhi
 */
@ApiModel(value = "招聘服务InviteServiceQueryExcelOutDto", description = "招聘服务InviteServiceQueryExcelOutDto")
public class InviteServiceQueryExcelOutDto {

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 部门UUID 被代理的企业 部门
     */
    @ApiModelProperty("部门UUID")
    private String departmentUuid;

    /**
     * 岗位UUID 被代理的企业 部门 岗位
     */
    @ApiModelProperty("岗位UUID")
    private String postUuid;

    /**
     * 招聘时间
     */
    @ApiModelProperty("招聘时间 yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    private Date inviteTime;

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

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }
}
