package com.relyme.linkOccupation.service.statistics.dto;

import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import java.util.List;

public class TimeStatisticOut extends BaseEntityForMysql {

    private List<TimePersonStatistic> timePersonStatistic;
    private List<TimePriceStatistic> timePriceStatistic;
    private List<TimeEnterpriseStatistic> timeEnterpriseStatistic;

    public List<TimePersonStatistic> getTimePersonStatistic() {
        return timePersonStatistic;
    }

    public void setTimePersonStatistic(List<TimePersonStatistic> timePersonStatistic) {
        this.timePersonStatistic = timePersonStatistic;
    }

    public List<TimePriceStatistic> getTimePriceStatistic() {
        return timePriceStatistic;
    }

    public void setTimePriceStatistic(List<TimePriceStatistic> timePriceStatistic) {
        this.timePriceStatistic = timePriceStatistic;
    }

    public List<TimeEnterpriseStatistic> getTimeEnterpriseStatistic() {
        return timeEnterpriseStatistic;
    }

    public void setTimeEnterpriseStatistic(List<TimeEnterpriseStatistic> timeEnterpriseStatistic) {
        this.timeEnterpriseStatistic = timeEnterpriseStatistic;
    }
}
