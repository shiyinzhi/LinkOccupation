package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服务进度信息ServiceStatusExtSelectedDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusExtSelectedDto", description = "服务进度信息ServiceStatusExtSelectedDto")
public class ServiceStatusExtSelectedDto {

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    /**
     * 服务内容uuid集合
     */
    @ApiModelProperty("服务内容uuid集合")
    private List<String> serviceDetailUuids;

    public List<String> getServiceDetailUuids() {
        return serviceDetailUuids;
    }

    public void setServiceDetailUuids(List<String> serviceDetailUuids) {
        this.serviceDetailUuids = serviceDetailUuids;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
