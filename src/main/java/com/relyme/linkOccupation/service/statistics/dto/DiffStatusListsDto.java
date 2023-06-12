package com.relyme.linkOccupation.service.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "异动统计DiffStatusListsDto", description = "异动统计DiffStatusListsDto")
public class DiffStatusListsDto {

    @ApiModelProperty("openid")
    private String openid;

    /**
     * 月份
     */
    @ApiModelProperty("月份 yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    private Date diffMonth;

    public Date getDiffMonth() {
        return diffMonth;
    }

    public void setDiffMonth(Date diffMonth) {
        this.diffMonth = diffMonth;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


}
