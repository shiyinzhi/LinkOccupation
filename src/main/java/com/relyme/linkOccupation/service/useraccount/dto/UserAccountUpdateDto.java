package com.relyme.linkOccupation.service.useraccount.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户账户信息UserAccountUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "用户账户信息UserAccountUpdateDto", description = "用户账户信息UserAccountUpdateDto")
public class UserAccountUpdateDto{

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;


    /**
     * 密码 使用MD5 加密
     */
    @ApiModelProperty("密码 使用MD5 加密")
    private String pwd;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
