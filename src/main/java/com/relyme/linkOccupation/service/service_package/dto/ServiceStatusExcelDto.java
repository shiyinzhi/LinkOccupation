package com.relyme.linkOccupation.service.service_package.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 服务状态信息ServiceStatusExcelDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务状态信息ServiceStatusExcelDto", description = "服务状态信息ServiceStatusExcelDto")
public class ServiceStatusExcelDto {

    /**
     * 更新时间 年月
     */
    @ApiModelProperty("更新时间yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    private Date updateDate;

    /**
     * 企业UUID
     */
    @ApiModelProperty("企业UUID")
    private String enterpriseUuid;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
