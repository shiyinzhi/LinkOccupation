package com.relyme.linkOccupation.service.rolling_picture.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 滚动图片信息RollingPictureDto
 * @author shiyinzhi
 */
@ApiModel(value = "滚动图片信息RollingPictureDto", description = "滚动图片信息RollingPictureDto")
public class RollingPictureDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * banner_name名称
     */
    @ApiModelProperty("名称")
    private String bannerName;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private int bannerOrder;


    /**
     * banner图片名称带后缀
     */
    @ApiModelProperty("banner图片名称带后缀")
    private String bannerTitle;

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(int bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }
}
