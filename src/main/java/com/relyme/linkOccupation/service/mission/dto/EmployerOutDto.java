package com.relyme.linkOccupation.service.mission.dto;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 雇主信息EmployerOutDto
 * @author shiyinzhi
 */
@ApiModel(value = "雇主信息EmployerOutDto", description = "雇主信息EmployerOutDto")
public class EmployerOutDto extends BaseEntityForMysql {

    /**
     * 雇主uuid
     */
    @ApiModelProperty("雇主uuid")
    private String employerUuid;

    /**
     * 雇主名称
     */
    @ApiModelProperty("雇主名称")
    private String employerName;


    /**
     * 雇主地址
     */
    @ApiModelProperty("雇主地址")
    private String employerAddress;

    /**
     * 雇主类型 0个人雇主 1企业雇主
     */
    @ApiModelProperty("雇主类型 0个人雇主 1企业雇主")
    private Integer employerType;



    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public Integer getEmployerType() {
        return employerType;
    }

    public void setEmployerType(Integer employerType) {
        this.employerType = employerType;
    }
}
