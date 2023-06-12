package com.relyme.linkOccupation.service.common.workertype.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工类型信息
 * @author shiyinzhi
 */
@ApiModel(value = "员工类型AddWorkerTypeDto", description = "员工类型AddWorkerTypeDto")
public class AddWorkerTypeDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时传入")
    private String uuid;

    /**
     * 员工类型名称
     */
    @ApiModelProperty("员工类型名称")
    private String workerTypeName;


    public String getWorkerTypeName() {
        return workerTypeName;
    }

    public void setWorkerTypeName(String workerTypeName) {
        this.workerTypeName = workerTypeName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
