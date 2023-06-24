package com.relyme.linkOccupation.service.legal_advice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 投诉建议信息ComplaintUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "投诉建议信息ComplaintUuidDto", description = "投诉建议信息ComplaintUuidDto")
public class LegalAdviceUuidDto {

    /**
     * 投诉建议uuid
     */
    @ApiModelProperty("投诉建议uuid")
    private String uuid;

    /**
     * 处理状态
     * -1暂不处理
     * 0待处理：管理员未处理，电话沟通二次确认，操作暂不处理，则状态变为已处理
     * 1处理中：已打电话联系，但用户还未点击满意
     * 2已处理：用户已评价
     */
    @ApiModelProperty("状态 -1暂不处理 0待处理 1处理中(电话已沟通) 2已处理")
    private Integer handleStatus;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }
}
