package com.relyme.linkOccupation.service.activityinfo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动信息ActivityInfoListsBackDto", description = "活动信息ActivityInfoListsBackDto")
public class ActivityInfoListsBackDto {

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
