package com.relyme.linkOccupation.service.statistics.domain;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.math.BigInteger;

public class DiffStatusList extends BaseEntityForMysql {

    public DiffStatusList(){}

    private BigInteger personCount;
    private String differentStatusUuid;
    private String differentStatusName;

    public BigInteger getPersonCount() {
        return personCount;
    }

    public void setPersonCount(BigInteger personCount) {
        this.personCount = personCount;
    }

    public String getDifferentStatusUuid() {
        return differentStatusUuid;
    }

    public void setDifferentStatusUuid(String differentStatusUuid) {
        this.differentStatusUuid = differentStatusUuid;
    }

    public String getDifferentStatusName() {
        return differentStatusName;
    }

    public void setDifferentStatusName(String differentStatusName) {
        this.differentStatusName = differentStatusName;
    }
}
