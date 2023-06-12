package com.relyme.linkOccupation.service.wechat_info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 微信信息WechatPhoneDto
 */
@ApiModel(value = "微信信息WechatPhoneDto", description = "微信信息WechatPhoneDto")
public class WechatPhoneDto {

    @NotNull(message = "code 不能为空！")
    @ApiModelProperty("code")
    private String code;

    @NotNull(message = "openid 不能为空！")
    @ApiModelProperty("openid")
    private String openid;

//    @NotNull(message = "unionId 不能为空！")
    @ApiModelProperty("unionId")
    private String unionid;

    /**
     * 传入的是SN通过sn查询uuid
     */
    @ApiModelProperty("推荐人uuid")
    private String referrerUuid;

    /**
     * 传入的是SN 推荐者的SN
     */
    @ApiModelProperty("推荐者的SN")
    private String sn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getReferrerUuid() {
        return referrerUuid;
    }

    public void setReferrerUuid(String referrerUuid) {
        this.referrerUuid = referrerUuid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
