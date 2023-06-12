package com.relyme.linkOccupation.service.employee.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇员账单EmployeeBillQueryDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇员账单EmployeeBillQueryDto", description = "雇员账单EmployeeBillQueryDto")
public class EmployeeBillQueryDto {


    @ApiModelProperty("当前页数")
    private int page = 1;

    @ApiModelProperty("每页显示条数")
    private int pageSize = 10;

    @ApiModelProperty("雇员uuid")
    private String employeeUuid;

    @ApiModelProperty("账单类型 1收获 2支出")
    private Integer searchType;

    /**
     * 时间
     */
    @ApiModelProperty("时间 yyy-MM")
    private String searchDate;

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

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }
}
