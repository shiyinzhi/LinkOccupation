package com.relyme.linkOccupation.service.common.category.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 职工类别信息
 * @author shiyinzhi
 */
@ApiModel(value = "职工类别信息AddCategoryInfoDto", description = "职工类别信息AddCategoryInfoDto")
public class AddCategoryInfoDto {


    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时传入")
    private String uuid;


    /**
     * 职工类别名称
     */
    @ApiModelProperty("职工类别名称")
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
