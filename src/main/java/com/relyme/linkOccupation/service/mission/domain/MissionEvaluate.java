package com.relyme.linkOccupation.service.mission.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 任务评价信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "mission_evaluate",indexes = {
        @Index(columnList = "uuid,mission_record_uuid")
})
public class MissionEvaluate extends BaseEntityForMysql {

    /**
     * 任务记录uuid
     */
    @Column(name = "mission_record_uuid", length = 36)
    private String missionRecordUuid;

    /**
     * 任务uuid
     */
    @Column(name = "mission_uuid", length = 36)
    private String missionUuid;

    /**
     * 评价者uuid
     */
    @Column(name = "evaluate_from_uuid", length = 36)
    private String evaluateFromUuid;


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
     * 是否已经累计信用分值 is_add
     */
    @Column(name = "is_add", length = 3,columnDefinition="tinyint default 0")
    private int isAdd;

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

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }
}
