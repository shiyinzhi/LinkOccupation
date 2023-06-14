package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 套餐服务详情信息ServiceDetailDto
 * @author shiyinzhi
 */
@ApiModel(value = "套餐服务详情信息ServiceDetailDto", description = "套餐服务详情信息ServiceDetailDto")
public class ServiceDetailDto {

    /**
     * 服务详情uuid
     */
    @ApiModelProperty("服务详情uuid")
    private String uuid;

    /**
     * 服务套餐uuid
     */
    @ApiModelProperty("服务套餐uuid")
    private String servicePackageUuid;

    /**
     * 内容描述
     */
    @ApiModelProperty("内容描述")
    private String serviceContent;

    /**
     * 服务次数
     */
    @ApiModelProperty("服务次数")
    private int serviceCount;

    /**
     * 服务有效期 可以是年或月份，具体为数字，以用户购买的选择单位为准
     */
    @ApiModelProperty("服务有效期")
    private int serviceLimit;

//    /**
//     * 开始时间
//     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @Column(name = "start_time")
//    private Date startTime;
//
//    /**
//     * 结束时间
//     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @Column(name = "end_time")
//    private Date endTime;

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getServiceLimit() {
        return serviceLimit;
    }

    public void setServiceLimit(int serviceLimit) {
        this.serviceLimit = serviceLimit;
    }

//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
