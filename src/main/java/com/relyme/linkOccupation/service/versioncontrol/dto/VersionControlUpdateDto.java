package com.relyme.linkOccupation.service.versioncontrol.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * 版本信息VersionControlUpdateDto
 * @author shiyinzhi
 */
@ApiModel(value = "版本信息VersionControlUpdateDto", description = "版本信息VersionControlUpdateDto")
public class VersionControlUpdateDto {

    /**
     * uuid
     */
    @ApiModelProperty("uuid 更新时使用")
    private String uuid;

    /**
     * 版本名称
     */
    @ApiModelProperty("版本名称")
    private String version;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
