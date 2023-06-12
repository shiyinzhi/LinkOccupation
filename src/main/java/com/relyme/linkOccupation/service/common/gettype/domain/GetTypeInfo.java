package com.relyme.linkOccupation.service.common.gettype.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 简历获取方式信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "get_type_info",indexes = {
        @Index(columnList = "uuid,get_type_name")
})
public class GetTypeInfo extends BaseEntityForMysql {


    /**
     * 简历获取方式名称
     */
    @Column(name = "get_type_name",length = 150)
    private String getTypeName;

    public String getGetTypeName() {
        return getTypeName;
    }

    public void setGetTypeName(String getTypeName) {
        this.getTypeName = getTypeName;
    }
}
