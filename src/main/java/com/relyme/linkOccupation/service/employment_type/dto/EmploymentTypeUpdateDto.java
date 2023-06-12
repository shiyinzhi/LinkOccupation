package com.relyme.linkOccupation.service.employment_type.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用工分类EmploymentTypeUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "用工分类EmploymentTypeUpdateDto", description = "用工分类EmploymentTypeUpdateDto")
public class EmploymentTypeUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String typeName;


    /**
     * icon名称带后缀
     */
    @ApiModelProperty("icon名称带后缀")
    private String typeIcon;

    /**
     * 类型  1.常规 2.临时工 3.长期工
     */
    @ApiModelProperty("类型  1.常规 2.临时工 3.长期工")
    private Integer typeOcname;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public Integer getTypeOcname() {
        return typeOcname;
    }

    public void setTypeOcname(Integer typeOcname) {
        this.typeOcname = typeOcname;
    }
}
