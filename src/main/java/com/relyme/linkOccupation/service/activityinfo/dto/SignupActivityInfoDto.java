package com.relyme.linkOccupation.service.activityinfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动信息SignupActivityInfoDto", description = "活动信息SignupActivityInfoDto")
public class SignupActivityInfoDto {

    @ApiModelProperty("openid")
    private String openid;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String mobile;


    /**
     * 活动唯一编号
     */
    @ApiModelProperty("活动唯一编号")
    private String activityUuid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActivityUuid() {
        return activityUuid;
    }

    public void setActivityUuid(String activityUuid) {
        this.activityUuid = activityUuid;
    }
}
