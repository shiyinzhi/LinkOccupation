package com.relyme.linkOccupation.service.service_package.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 服务进度信息ServiceStatusUuidListDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务进度信息ServiceStatusUuidListDto", description = "服务进度信息ServiceStatusUuidListDto")
public class ServiceStatusUuidListDto {

    /**
     * 服务进度集合
     */
    @ApiModelProperty("服务进度集合")
    private List<ServiceStatusDto> serviceStatusDtoList;


    public List<ServiceStatusDto> getServiceStatusDtoList() {
        return serviceStatusDtoList;
    }

    public void setServiceStatusDtoList(List<ServiceStatusDto> serviceStatusDtoList) {
        this.serviceStatusDtoList = serviceStatusDtoList;
    }
}
