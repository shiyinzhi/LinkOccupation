package com.relyme.linkOccupation.service.roster.dao;


import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RosterDao extends ExtJpaRepository<Roster, String>, JpaSpecificationExecutor<Roster> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Roster findByUuid(String uuid);

    /**
     * 通过企业编号和手机号查询企业花名册信息
     * @param enterpriseUuid
     * @param mobile
     * @return
     */
    Roster findByEnterpriseUuidAndMobile(String enterpriseUuid,String mobile);


    /**
     * 通过工号查询人员花名册
     * @param jobNumber
     * @return
     */
    Roster findByJobNumber(String jobNumber);

    /**
     * 通过部门UUID和企业uuid查询通讯录
     * @param departmentUuid
     * @param enterpriseUuid
     * @return
     */
    List<Roster> findByDepartmentUuidAndEnterpriseUuid(String departmentUuid,String enterpriseUuid);

    /**
     * 通过手机号/企业编号查询花名册信息
     * @param mobile
     * @param enterpriseUuid
     * @return
     */
    Roster findByMobileAndEnterpriseUuid(String mobile,String enterpriseUuid);


    /**
     * 根据月份查询人事异动总览
     * @param monthStart
     * @param monthEnd
     * @return
     */
    @Query(value = "select count(a.uuid) personCount,a.different_status_uuid,a.different_status_name from (" +
            "select dsr.uuid,DATE_FORMAT(dsr.add_time,'%Y-%m') rmonth,ros.different_status_uuid,ds.different_status_name " +
            "from different_status_record dsr,roster ros,different_status ds where roster_uuid = ros.uuid and " +
            "ros.different_status_uuid = ds.uuid and dsr.add_time >= ?1 and dsr.add_time <=?2) a " +
            "group by a.rmonth,a.different_status_uuid",nativeQuery = true)
    Object[] differentStatusStatistics(String monthStart,String monthEnd);



    /**
     * 花名册 本月入职人数
     * @return
     */
    @Query(value = "select count(*) from roster where active = 1 and join_data >=?1 and join_data <=?2",nativeQuery = true)
    int findJoinThisMonthCount(String startTime,String endTime);


    /**
     * 花名册 本月离职人数
     * @return
     */
    @Query(value = "select count(*) from roster where active = 1 and leave_data >=?1 and leave_data <=?2",nativeQuery = true)
    int findLeaveThisMonthCount(String startTime,String endTime);
}
