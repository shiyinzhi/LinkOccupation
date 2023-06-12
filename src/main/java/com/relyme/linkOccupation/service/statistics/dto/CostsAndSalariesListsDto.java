package com.relyme.linkOccupation.service.statistics.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "统计信息CostsAndSalariesListsDto", description = "统计信息CostsAndSalariesListsDto")
public class CostsAndSalariesListsDto {

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

//    type为1，代表最近30天
//    type为2，代表季度
//    type为3，代表月份

    /**
     * 结束时间
     */
    @ApiModelProperty("1，代表最近30天 2，代表季度 3，代表月份")
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
