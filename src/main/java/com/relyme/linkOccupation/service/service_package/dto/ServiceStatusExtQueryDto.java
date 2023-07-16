package com.relyme.linkOccupation.service.service_package.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 服务状态信息ServiceStatusExtQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "服务状态信息ServiceStatusExtQueryDto", description = "服务状态信息ServiceStatusExtQueryDto")
public class ServiceStatusExtQueryDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;


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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

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
