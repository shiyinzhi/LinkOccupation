package com.relyme.linkOccupation.service.audit_manage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 黑名单BlackListClearDto
 * @author shiyinzhi
 */
@ApiModel(value = "黑名单BlackListClearDto", description = "黑名单BlackListClearDto")
public class BlackListClearDto {


    @ApiModelProperty("用户类型uuid")
    private String custTypeUuid;

    /**
     * 用户类型 1雇员 2个人雇主 3企业雇主
     */
    @ApiModelProperty("用户类型 1雇员 2个人雇主 3企业雇主")
    private Integer custType;

    public String getCustTypeUuid() {
        return custTypeUuid;
    }

    public void setCustTypeUuid(String custTypeUuid) {
        this.custTypeUuid = custTypeUuid;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }
}
