package com.relyme.linkOccupation.service.statistics.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "统计信息CostsAndSalariesListsTopDto", description = "统计信息CostsAndSalariesListsTopDto")
public class CostsAndSalariesListsTopDto {

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间 yyyy-MM-dd")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间 yyyy-MM")
    private String endTime;

    /**
     * 排行位数
     */
    @ApiModelProperty("排行位数")
    private Integer orderCount;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
