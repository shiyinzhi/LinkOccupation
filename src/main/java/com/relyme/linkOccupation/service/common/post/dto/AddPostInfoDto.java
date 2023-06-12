package com.relyme.linkOccupation.service.common.post.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作岗位信息
 * @author shiyinzhi
 */
@ApiModel(value = "岗位信息AddPostInfoDto", description = "岗位信息AddPostInfoDto")
public class AddPostInfoDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时传入")
    private String uuid;

    /**
     * 工作岗位名称
     */
    @ApiModelProperty("工作岗位名称")
    private Integer postNo;

    /**
     * 工作岗位名称
     */
    @ApiModelProperty("工作岗位名称")
    private String postName;

    /**
     * 父级岗位编号
     */
    @ApiModelProperty("父级岗位编号")
    private Integer parentNo;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public Integer getParentNo() {
        return parentNo;
    }

    public void setParentNo(Integer parentNo) {
        this.parentNo = parentNo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
