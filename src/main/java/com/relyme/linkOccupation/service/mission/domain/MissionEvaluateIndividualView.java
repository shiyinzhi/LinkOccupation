package com.relyme.linkOccupation.service.mission.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 任务评价信息 个人雇主
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_individual_mission_evaluate",indexes = {
        @Index(columnList = "uuid,mission_record_uuid")
})
public class MissionEvaluateIndividualView extends BaseEntityForMysql {

    /**
     * 任务记录uuid
     */
    @Column(name = "mission_record_uuid", length = 36)
    private String missionRecordUuid;

    /**
     * 评价分数
     */
    @Column(name = "evaluate_score", length = 5)
    private int evaluateScore;

    /**
     * 评价内容，多个使用逗号分隔
     */
    @Column(name = "evaluate_content", length = 256)
    private String evaluateRontent;

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
     * 个人名称
     */
    @Column(name = "individual_name",length = 150)
    private String individualName;

    /**
     * 提交人/确认人 关联客户账户
     */
    @Column(name = "cust_account_uuid",length = 36)
    private String custAccountUuid;

    /**
     * 头像名称包括后缀
     */
    @Column(name = "picture_url",length = 512)
    private String pictureURL;

    public String getMissionRecordUuid() {
        return missionRecordUuid;
    }

    public void setMissionRecordUuid(String missionRecordUuid) {
        this.missionRecordUuid = missionRecordUuid;
    }

    public int getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(int evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public String getEvaluateRontent() {
        return evaluateRontent;
    }

    public void setEvaluateRontent(String evaluateRontent) {
        this.evaluateRontent = evaluateRontent;
    }

    public String getEmployerUuid() {
        return employerUuid;
    }

    public void setEmployerUuid(String employerUuid) {
        this.employerUuid = employerUuid;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
