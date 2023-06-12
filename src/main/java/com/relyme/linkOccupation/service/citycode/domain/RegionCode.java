package com.relyme.linkOccupation.service.citycode.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 区划信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "region_code",indexes = {
        @Index(columnList = "uuid,city_name,city_code")
})
public class RegionCode extends BaseEntityForMysql {


    /**
     * 区划名称
     */
    @Column(name = "city_name",length = 128)
    private String cityName;

    /**
     * 父级区划编码
     */
    @Column(name = "parent_uuid",length = 36)
    private String parentUuid;

    /**
     * 区划编码
     */
    @Column(name = "city_code",length = 128)
    private String cityCode;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
