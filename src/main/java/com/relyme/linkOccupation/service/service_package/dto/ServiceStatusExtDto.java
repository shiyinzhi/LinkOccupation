package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务进度信息ServiceStatusExtDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusExtDto", description = "服务进度信息ServiceStatusExtDto")
public class ServiceStatusExtDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 服务名称
     */
    @ApiModelProperty("服务名称")
    private String serviceContent;

    /**
     * 服务描述
     */
    @ApiModelProperty("服务描述")
    private String remark;

    /**
     * 剩余服务次数
     */
    @ApiModelProperty("剩余服务次数")
    private int serviceCount;

    /**
     * 服务有效期 可以是年或月份，具体为数字，以用户购买的选择单位为准
     */
    @ApiModelProperty("服务有效期")
    private int serviceLimit;

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
