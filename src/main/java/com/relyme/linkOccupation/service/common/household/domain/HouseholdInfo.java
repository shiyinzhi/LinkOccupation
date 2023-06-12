package com.relyme.linkOccupation.service.common.household.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 户口类型信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "household_info",indexes = {
        @Index(columnList = "uuid,household_name")
})
public class HouseholdInfo extends BaseEntityForMysql {


    /**
     * 户口类型名称
     */
    @Column(name = "household_name",length = 150)
    private String householdName;

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }
}
