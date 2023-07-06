package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 服务进度信息ServiceStatusDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusDto", description = "服务进度信息ServiceStatusDto")
public class ServiceStatusDto {

    /**
     * 服务进度uuid
     */
    @ApiModelProperty("服务进度uuid")
    private String uuid;

    /**
     * 服务进度 默认进度为0
     */
    @ApiModelProperty("服务进度")
    BigDecimal statusProcess;

    /**
     * 服务次数
     */
    @ApiModelProperty("服务次数")
    private Integer serviceCount;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(BigDecimal statusProcess) {
        this.statusProcess = statusProcess;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }
}
