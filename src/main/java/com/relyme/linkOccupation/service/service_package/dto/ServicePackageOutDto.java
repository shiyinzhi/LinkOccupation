package com.relyme.linkOccupation.service.service_package.dto;


import com.relyme.linkOccupation.service.service_package.domain.ServiceDetail;
import com.relyme.linkOccupation.service.service_package.domain.ServicePrices;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 套餐服务信息ServicePackageOutDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务信息ServicePackageOutDto", description = "套餐服务信息ServicePackageOutDto")
public class ServicePackageOutDto extends BaseEntityForMysql {

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

    /**
     * 服务内容
     */
    @ApiModelProperty("服务内容")
    private List<ServiceDetail> serviceDetailList;

    /**
     * 服务价格
     */
    @ApiModelProperty("服务价格")
    private List<ServicePrices> servicePricesList;

    /**
     * 套餐图片路径
     */
    @ApiModelProperty("套餐图片路径")
    private String coverFilePath;

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

    public List<ServiceDetail> getServiceDetailList() {
        return serviceDetailList;
    }

    public void setServiceDetailList(List<ServiceDetail> serviceDetailList) {
        this.serviceDetailList = serviceDetailList;
    }

    public List<ServicePrices> getServicePricesList() {
        return servicePricesList;
    }

    public void setServicePricesList(List<ServicePrices> servicePricesList) {
        this.servicePricesList = servicePricesList;
    }

    public String getCoverFilePath() {
        return coverFilePath;
    }

    public void setCoverFilePath(String coverFilePath) {
        this.coverFilePath = coverFilePath;
    }
}
