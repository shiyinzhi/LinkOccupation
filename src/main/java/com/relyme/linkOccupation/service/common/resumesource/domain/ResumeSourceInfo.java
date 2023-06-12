package com.relyme.linkOccupation.service.common.resumesource.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 简历来源信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_source_info",indexes = {
        @Index(columnList = "uuid,resume_source_name")
})
public class ResumeSourceInfo extends BaseEntityForMysql {


    /**
     * 简历来源名称
     */
    @Column(name = "resume_source_name",length = 150)
    private String resumeSourceName;

    public String getResumeSourceName() {
        return resumeSourceName;
    }

    public void setResumeSourceName(String resumeSourceName) {
        this.resumeSourceName = resumeSourceName;
    }
}
