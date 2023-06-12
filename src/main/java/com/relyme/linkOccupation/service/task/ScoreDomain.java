package com.relyme.linkOccupation.service.task;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ScoreDomain {

    private String missionUuid;
    private BigInteger personCount;
    private BigDecimal scoreTotal;

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public BigInteger getPersonCount() {
        return personCount;
    }

    public void setPersonCount(BigInteger personCount) {
        this.personCount = personCount;
    }

    public BigDecimal getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(BigDecimal scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
