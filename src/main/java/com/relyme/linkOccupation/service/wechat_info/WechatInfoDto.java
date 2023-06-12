package com.relyme.linkOccupation.service.wechat_info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信信息WechatInfoDto", description = "微信信息WechatInfoDto")
public class WechatInfoDto {

    @ApiModelProperty("code")
    private String code;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;

    /**
     * 头像名称包括后缀
     */@ApiModelProperty("头像名称包括后缀")

    private String pictureURL;

    /**
     * 城市
     */
    @ApiModelProperty("城市")
    private String city;

    /**
     * 省份
     */
    @ApiModelProperty("省份")
    private String province;

    /**
     * 县
     */
    @ApiModelProperty("县")
    private String country;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
