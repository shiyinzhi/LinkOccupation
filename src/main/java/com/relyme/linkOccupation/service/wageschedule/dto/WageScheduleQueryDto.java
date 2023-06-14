package com.relyme.linkOccupation.service.wageschedule.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 工资表信息WageScheduleQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "工资表信息WageScheduleQueryDto", description = "工资表信息WageScheduleQueryDto")
public class WageScheduleQueryDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    /**
     * 查询条件
     */
    @ApiModelProperty("查询条件")
    private String searStr;


    /**
     * 发放时间 年月
     */
    @ApiModelProperty("发放时间yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    private Date publishDate;

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

    public String getSearStr() {
        return searStr;
    }

    public void setSearStr(String searStr) {
        this.searStr = searStr;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
