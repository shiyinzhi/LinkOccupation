package com.relyme.linkOccupation.service.wageschedule.dao;


import com.relyme.linkOccupation.service.wageschedule.domain.WageSchedule;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface WageScheduleDao extends ExtJpaRepository<WageSchedule, String>, JpaSpecificationExecutor<WageSchedule> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    WageSchedule findByUuid(String uuid);

    /**
     * 批量查询代缴社保信息
     * @param uuids
     * @return
     */
    List<WageSchedule> findByUuidIn(List<String> uuids);


    /**
     * 通过手机号、工资月份、状态查询工资信息
     * @param wageMonth
     * @param rosterPhone
     * @param active
     * @return
     */
    WageSchedule findByWageMonthAndRosterPhoneAndActive(Date wageMonth,String rosterPhone,int active);


    /**
     * 通过手机号、工资月份、补发月份、状态查询工资信息
     * @param wageMonth
     * @param rosterPhone
     * @param active
     * @return
     */
    WageSchedule findByWageMonthAndRosterPhoneAndBackPayTimeAndActive(Date wageMonth,String rosterPhone,Date backPayTime,int active);
}
