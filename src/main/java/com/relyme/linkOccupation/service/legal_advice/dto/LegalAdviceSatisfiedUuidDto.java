package com.relyme.linkOccupation.service.legal_advice.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 法律咨询信息legalAdviceSatisfiedUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "法律咨询信息legalAdviceSatisfiedUuidDto", description = "法律咨询信息legalAdviceSatisfiedUuidDto")
public class LegalAdviceSatisfiedUuidDto {

    /**
     * 法律咨询uuid
     */
    @ApiModelProperty("法律咨询uuid")
    private String uuid;

    /**
     * 是否满意  0不满意 1满意
     */
    @ApiModelProperty("是否满意  0不满意 1满意")
    private Integer handleSatisfied;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getHandleSatisfied() {
        return handleSatisfied;
    }

    public void setHandleSatisfied(Integer handleSatisfied) {
        this.handleSatisfied = handleSatisfied;
    }
}
