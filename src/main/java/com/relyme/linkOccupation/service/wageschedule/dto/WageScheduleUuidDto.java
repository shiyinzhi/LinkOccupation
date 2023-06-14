package com.relyme.linkOccupation.service.wageschedule.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工资表信息WageScheduleUuidDto
 * @author shiyinzhi
 */
@ApiModel(value = "工资表信息WageScheduleUuidDto", description = "工资表信息WageScheduleUuidDto")
public class WageScheduleUuidDto {

    /**
     * 工资信息uuid
     */
    @ApiModelProperty("工资信息uuid")
    private String uuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
