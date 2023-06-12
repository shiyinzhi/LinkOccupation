package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

public class CustCountStatisticOut extends BaseEntityForMysql {

    private int totalRegistCount;
    private int shareRegistCount;

    public int getTotalRegistCount() {
        return totalRegistCount;
    }

    public void setTotalRegistCount(int totalRegistCount) {
        this.totalRegistCount = totalRegistCount;
    }

    public int getShareRegistCount() {
        return shareRegistCount;
    }

    public void setShareRegistCount(int shareRegistCount) {
        this.shareRegistCount = shareRegistCount;
    }
}
