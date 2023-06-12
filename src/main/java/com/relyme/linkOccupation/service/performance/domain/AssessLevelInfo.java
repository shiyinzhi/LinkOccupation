package com.relyme.linkOccupation.service.performance.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 考核等级信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "assess_level_info",indexes = {
        @Index(columnList = "uuid,assess_level_name")
})
public class AssessLevelInfo extends BaseEntityForMysql {


    /**
     * 考核成绩最低值
     */
    @Column(name = "level_score_low",length = 18,scale = 2)
    private BigDecimal levelScoreLow;

    /**
     * 考核成绩最高值
     */
    @Column(name = "level_score_high",length = 18,scale = 2)
    private BigDecimal levelScoreHigh;

    /**
     * 考核等级名称
     */
    @Column(name = "assess_level_name",length = 150)
    private String assessLevelName;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    public String getAssessLevelName() {
        return assessLevelName;
    }

    public void setAssessLevelName(String assessLevelName) {
        this.assessLevelName = assessLevelName;
    }

    public BigDecimal getLevelScoreLow() {
        return levelScoreLow;
    }

    public void setLevelScoreLow(BigDecimal levelScoreLow) {
        this.levelScoreLow = levelScoreLow;
    }

    public BigDecimal getLevelScoreHigh() {
        return levelScoreHigh;
    }

    public void setLevelScoreHigh(BigDecimal levelScoreHigh) {
        this.levelScoreHigh = levelScoreHigh;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }
}
