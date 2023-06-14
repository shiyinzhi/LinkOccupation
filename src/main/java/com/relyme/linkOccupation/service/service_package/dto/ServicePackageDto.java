package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务信息ServicePackageDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务信息ServicePackageDto", description = "套餐服务信息ServicePackageDto")
public class ServicePackageDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String packageName;

    /**
     * 封面图片名称
     */
    @ApiModelProperty("封面图片名称")
    private String coverFileName;

    /**
     * 是否下架 1下架 0上架
     */
    @ApiModelProperty("是否下架 1下架 0上架")
    private int isClose;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
