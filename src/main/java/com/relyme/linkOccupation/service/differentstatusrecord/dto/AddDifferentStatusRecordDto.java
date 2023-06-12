package com.relyme.linkOccupation.service.differentstatusrecord.dto;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "异动信息AddDifferentStatusRecordDto", description = "异动信息AddDifferentStatusRecordDto")
public class AddDifferentStatusRecordDto extends BaseEntityForMysql {

    /**
     * 工号
     * 工号：QYJC-01-000001
     * 含义：企业简称-岗位性质-工号员工
     * 岗位性质：01表示全职人员、02表示退休返聘人员、03表示兼职和临时工等、04表示其他类
     */
    @ApiModelProperty("工号")
    private String jobNumber;

    /**
     * 新工号
     */
    @ApiModelProperty("新工号")
    private String jobNumberNew;


    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private String departmentUuid;

    /**
     * 新部门
     */
    @ApiModelProperty("新部门")
    private String departmentUuidNew;

    /**
     * 工作岗位
     */
    @ApiModelProperty("工作岗位")
    private String postUuid;

    /**
     * 新工作岗位
     */
    @ApiModelProperty("新工作岗位")
    private String postUuidNew;

    /**
     * 职工类别
     */
    @ApiModelProperty("职工类别")
    private String categoryUuid;

    /**
     * 职工类别
     */
    @ApiModelProperty("职工类别")
    private String categoryUuidNew;


    /**
     * 异动状态
     */
    @ApiModelProperty("异动状态")
    private String differentStatusUuid;


    /**
     * 花名册uuid
     */
    @ApiModelProperty("花名册uuid")
    private String rosterUuid;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobNumberNew() {
        return jobNumberNew;
    }

    public void setJobNumberNew(String jobNumberNew) {
        this.jobNumberNew = jobNumberNew;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getDepartmentUuidNew() {
        return departmentUuidNew;
    }

    public void setDepartmentUuidNew(String departmentUuidNew) {
        this.departmentUuidNew = departmentUuidNew;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public String getPostUuidNew() {
        return postUuidNew;
    }

    public void setPostUuidNew(String postUuidNew) {
        this.postUuidNew = postUuidNew;
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public String getCategoryUuidNew() {
        return categoryUuidNew;
    }

    public void setCategoryUuidNew(String categoryUuidNew) {
        this.categoryUuidNew = categoryUuidNew;
    }

    public String getDifferentStatusUuid() {
        return differentStatusUuid;
    }

    public void setDifferentStatusUuid(String differentStatusUuid) {
        this.differentStatusUuid = differentStatusUuid;
    }

    public String getRosterUuid() {
        return rosterUuid;
    }

    public void setRosterUuid(String rosterUuid) {
        this.rosterUuid = rosterUuid;
    }
}
