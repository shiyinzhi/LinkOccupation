package com.relyme.linkOccupation.service.common.education.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 学历信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "education_info",indexes = {
        @Index(columnList = "uuid,education_name")
})
public class EducationInfo extends BaseEntityForMysql {


    /**
     * 学历名称
     */
    @Column(name = "education_name",length = 150)
    private String educationName;

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }
}
