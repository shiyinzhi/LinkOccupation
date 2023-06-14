package com.relyme.linkOccupation.service.service_package.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 套餐服务信息视图
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_service_package",indexes = {
        @Index(columnList = "uuid,user_account_uuid,package_name")
})
public class ServicePackageView extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 姓名
     */
    @Column(name = "name",length = 128,nullable = false)
    private String userName;

    /**
     * 套餐名称
     */
    @Column(name = "package_name",length = 128)
    private String packageName;

    /**
     * 封面图片名称
     */
    @Column(name = "cover_file_name",length = 128,nullable = false)
    private String coverFileName;

    /**
     * 是否下架 0下架 1上架
     */
    @Column(name = "is_close", length = 3,columnDefinition="tinyint default 1")
    private int isClose;

    @Transient
    private String coverFilePath;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCoverFileName() {
        return coverFileName;
    }

    public void setCoverFileName(String coverFileName) {
        this.coverFileName = coverFileName;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCoverFilePath() {
        return coverFilePath;
    }

    public void setCoverFilePath(String coverFilePath) {
        this.coverFilePath = coverFilePath;
    }
}
