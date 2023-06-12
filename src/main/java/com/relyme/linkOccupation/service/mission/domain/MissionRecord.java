package com.relyme.linkOccupation.service.mission.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 任务记录信息信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "mission_record",indexes = {
        @Index(columnList = "uuid,employer_uuid,employee_uuid,employee_uuid")
})
public class MissionRecord extends BaseEntityForMysql {

    /**
     * 雇主uuid 客户uuid
     */
    @Column(name = "employer_uuid", length = 36)
    private String employerUuid;


    /**
     * 雇员uuid 客户uuid
     */
    @Column(name = "employee_uuid", length = 36)
    private String employeeUuid;

    /**
     * 任务uuid
     */
    @Column(name = "mission_uuid", length = 36)
    private String missionUuid;

    /**
     * 雇主类型 2个人雇主 3企业雇主
     */
    @Column(name = "employer_type", length = 3,columnDefinition="tinyint default 0")
    private int employerType;

    /**
     * 雇员端状态 0待确认 1雇主已确认 2雇主已拒绝 3雇员已拒绝 4雇员已确认 5双方已确认 6雇员确认已完成 7待评价 8已评价
     */
    @Column(name = "mission_record_status", length = 3,columnDefinition="tinyint default 0")
    private int missionRecordStatus;

    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public int getEmployerType() {
        return employerType;
    }

    public void setEmployerType(int employerType) {
        this.employerType = employerType;
    }

    public int getMissionRecordStatus() {
        return missionRecordStatus;
    }

    public void setMissionRecordStatus(int missionRecordStatus) {
        this.missionRecordStatus = missionRecordStatus;
    }
}
