package com.relyme.linkOccupation.service.wageschedule.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 工资信息WageScheduleUuidListDto
 * @author shiyinzhi
 */
@ApiModel(value = "工资信息WageScheduleUuidListDto", description = "工资信息WageScheduleUuidListDto")
public class WageScheduleUuidListDto {

    /**
     * 工资信息uuid
     */
    @ApiModelProperty("工资信息uuid")
    private List<String> uuids;


    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }
}
