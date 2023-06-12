package com.relyme.linkOccupation.service.mission.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 任务评价信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_mission_evaluate_all",indexes = {
        @Index(columnList = "uuid,mission_record_uuid")
})
public class MissionEvaluateViewAll extends BaseEntityForMysql {

    /**
     * 任务记录uuid
     */
    @Column(name = "mission_record_uuid", length = 36)
    private String missionRecordUuid;

    /**
     * 评价者uuid
     */
    @Column(name = "evaluate_from_uuid", length = 36)
    private String evaluateFromUuid;

    /**
     * 任务uuid
     */
    @Column(name = "mission_uuid", length = 36)
    private String missionUuid;


    /**
     * 被评价者uuid
     */
    @Column(name = "evaluate_to_uuid", length = 36)
    private String evaluateToUuid;

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
     * 评论者 雇员名称
     */
    @Column(name = "from_employee_name", length = 128)
    private String fromEmployeeName;

    /**
     * 评论者 雇员头像
     */
    @Column(name = "from_employee_picture_url", length = 128)
    private String fromEmployeePictureUrl;


    /**
     * 评论者 企业名称
     */
    @Column(name = "from_enterprise_name", length = 128)
    private String fromEnterpriseName;

    /**
     * 评论者 企业头像
     */
    @Column(name = "from_enterprise_info_picture_url", length = 128)
    private String fromEnterpriseInfoPictureUrl;


    /**
     * 评论者 个体户名称
     */
    @Column(name = "from_individual_name", length = 128)
    private String fromIndividualName;

    /**
     * 评论者 个体户头像
     */
    @Column(name = "from_individual_employers_picture_url", length = 128)
    private String fromIndividualEmployersPictureUrl;


    /**
     * 被评论者 雇员名称
     */
    @Column(name = "to_employee_name", length = 128)
    private String toEmployeeName;

    /**
     * 被评论者 雇员头像
     */
    @Column(name = "to_employee_picture_url", length = 128)
    private String toEmployeePictureUrl;


    /**
     * 被评论者 企业名称
     */
    @Column(name = "to_enterprise_name", length = 128)
    private String toEnterpriseName;

    /**
     * 被评论者 企业头像
     */
    @Column(name = "to_enterprise_info_picture_url", length = 128)
    private String toEnterpriseInfoPictureUrl;


    /**
     * 被评论者 个体户名称
     */
    @Column(name = "to_individual_name", length = 128)
    private String toIndividualName;

    /**
     * 被评论者 个体户头像
     */
    @Column(name = "to_individual_employers_picture_url", length = 128)
    private String toIndividualEmployersPictureUrl;


    @Transient
    private String evaluaterName;

    @Transient
    private String evaluaterPicPath;


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

    public String getEvaluateFromUuid() {
        return evaluateFromUuid;
    }

    public void setEvaluateFromUuid(String evaluateFromUuid) {
        this.evaluateFromUuid = evaluateFromUuid;
    }

    public String getEvaluateToUuid() {
        return evaluateToUuid;
    }

    public void setEvaluateToUuid(String evaluateToUuid) {
        this.evaluateToUuid = evaluateToUuid;
    }

    public String getFromEmployeeName() {
        return fromEmployeeName;
    }

    public void setFromEmployeeName(String fromEmployeeName) {
        this.fromEmployeeName = fromEmployeeName;
    }

    public String getFromEmployeePictureUrl() {
        return fromEmployeePictureUrl;
    }

    public void setFromEmployeePictureUrl(String fromEmployeePictureUrl) {
        this.fromEmployeePictureUrl = fromEmployeePictureUrl;
    }

    public String getFromEnterpriseName() {
        return fromEnterpriseName;
    }

    public void setFromEnterpriseName(String fromEnterpriseName) {
        this.fromEnterpriseName = fromEnterpriseName;
    }

    public String getFromEnterpriseInfoPictureUrl() {
        return fromEnterpriseInfoPictureUrl;
    }

    public void setFromEnterpriseInfoPictureUrl(String fromEnterpriseInfoPictureUrl) {
        this.fromEnterpriseInfoPictureUrl = fromEnterpriseInfoPictureUrl;
    }

    public String getFromIndividualName() {
        return fromIndividualName;
    }

    public void setFromIndividualName(String fromIndividualName) {
        this.fromIndividualName = fromIndividualName;
    }

    public String getFromIndividualEmployersPictureUrl() {
        return fromIndividualEmployersPictureUrl;
    }

    public void setFromIndividualEmployersPictureUrl(String fromIndividualEmployersPictureUrl) {
        this.fromIndividualEmployersPictureUrl = fromIndividualEmployersPictureUrl;
    }

    public String getToEmployeeName() {
        return toEmployeeName;
    }

    public void setToEmployeeName(String toEmployeeName) {
        this.toEmployeeName = toEmployeeName;
    }

    public String getToEmployeePictureUrl() {
        return toEmployeePictureUrl;
    }

    public void setToEmployeePictureUrl(String toEmployeePictureUrl) {
        this.toEmployeePictureUrl = toEmployeePictureUrl;
    }

    public String getToEnterpriseName() {
        return toEnterpriseName;
    }

    public void setToEnterpriseName(String toEnterpriseName) {
        this.toEnterpriseName = toEnterpriseName;
    }

    public String getToEnterpriseInfoPictureUrl() {
        return toEnterpriseInfoPictureUrl;
    }

    public void setToEnterpriseInfoPictureUrl(String toEnterpriseInfoPictureUrl) {
        this.toEnterpriseInfoPictureUrl = toEnterpriseInfoPictureUrl;
    }

    public String getToIndividualName() {
        return toIndividualName;
    }

    public void setToIndividualName(String toIndividualName) {
        this.toIndividualName = toIndividualName;
    }

    public String getToIndividualEmployersPictureUrl() {
        return toIndividualEmployersPictureUrl;
    }

    public void setToIndividualEmployersPictureUrl(String toIndividualEmployersPictureUrl) {
        this.toIndividualEmployersPictureUrl = toIndividualEmployersPictureUrl;
    }

    public String getEvaluaterName() {
        return evaluaterName;
    }

    public void setEvaluaterName(String evaluaterName) {
        this.evaluaterName = evaluaterName;
    }

    public String getEvaluaterPicPath() {
        return evaluaterPicPath;
    }

    public void setEvaluaterPicPath(String evaluaterPicPath) {
        this.evaluaterPicPath = evaluaterPicPath;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }
}
