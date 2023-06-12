package com.relyme.linkOccupation.service.common.politicsstatus.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 政治面貌信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "politics_status",indexes = {
        @Index(columnList = "uuid,politics_status_name")
})
public class PoliticsStatus extends BaseEntityForMysql {


    /**
     * 政治面貌名称
     */
    @Column(name = "politics_status_name",length = 150)
    private String politicsStatusName;

    public String getPoliticsStatusName() {
        return politicsStatusName;
    }

    public void setPoliticsStatusName(String politicsStatusName) {
        this.politicsStatusName = politicsStatusName;
    }
}
