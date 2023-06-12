package com.relyme.linkOccupation.service.rolling_picture.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 滚动图片信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "rolling_picture",indexes = {
        @Index(columnList = "uuid")
})
public class RollingPicture extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * banner_name名称
     */
    @Column(name = "banner_name", length = 128)
    private String bannerName;

    /**
     * 排序
     */
    @Column(name = "banner_order", length = 5)
    private int bannerOrder;


    /**
     * banner图片名称带后缀
     */
    @Column(name = "banner_title", length = 512)
    private String bannerTitle;

    @Transient
    private String bannerPath;


    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
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

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
