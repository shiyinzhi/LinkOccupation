package com.relyme.linkOccupation.service.versioncontrol.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 版本信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "version_control",indexes = {
        @Index(columnList = "uuid,version")
})
public class VersionControl extends BaseEntityForMysql {


    /**
     * 版本名称
     */
    @Column(name = "version",length = 128)
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
