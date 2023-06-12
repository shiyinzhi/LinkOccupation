package com.relyme.linkOccupation.service.common.household.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "户口类型信息HouseholdInfoListsDto", description = "户口类型信息HouseholdInfoListsDto")
public class HouseholdInfoListsDto {

    @ApiModelProperty("户口类型名称")
    private String householdName;

    @ApiModelProperty("企业编号uuid")
    private String enterpriseUuid;

    @ApiModelProperty("page")
    private int page = 1;

    @ApiModelProperty("pageSize")
    private int pageSize = 10;

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

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

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

}
