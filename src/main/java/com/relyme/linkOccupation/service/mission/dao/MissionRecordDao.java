package com.relyme.linkOccupation.service.mission.dao;


import com.relyme.linkOccupation.service.mission.domain.MissionRecord;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MissionRecordDao extends ExtJpaRepository<MissionRecord, String>, JpaSpecificationExecutor<MissionRecord> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    MissionRecord findByUuid(String uuid);

    /**
     * 通过任务uuid查询任务记录信息
     * @param missionUuid
     * @return
     */
    List<MissionRecord> findByMissionUuid(String missionUuid);

    /**
     * 通过uuid 集合查询任务记录信息
     * @param uuids
     * @return
     */
    List<MissionRecord> findByUuidIn(List<String> uuids);


    /**
     * 通过任务和雇员uuid 查询任务信息
     * @param missionUuid
     * @param employeeUuid
     * @return
     */
    MissionRecord findByMissionUuidAndEmployeeUuid(String missionUuid,String employeeUuid);


    /**
     * 通过任务uuid 雇员uuid 雇主uuid 查询信息
     * @param missionUuid
     * @param employeeUuid
     * @param employerUuid
     * @return
     */
    MissionRecord findByMissionUuidAndEmployeeUuidAndEmployerUuid(String missionUuid,String employeeUuid,String employerUuid);

    /**
     * 查询未评价人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where mission_record_status <= 7 and update_time <=?1 and mission_uuid=?2",nativeQuery = true)
    int getUnEvaluateCount(String endTime,String missionUuid);

    /**
     * 查询未评价人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where (mission_record_status = 7 or mission_record_status=10 or mission_record_status=11) and update_time <=?1",nativeQuery = true)
    int getUnEvaluateCountByTime(String endTime);


    /**
     * 查询未评价人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where (mission_record_status = 7 or mission_record_status = 11) and mission_uuid=?1",nativeQuery = true)
    int getUnEvaluateCountByMissionUuid(String missionUuid);


    /**
     * 查询未完成人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where (mission_record_status = 5 or mission_record_status = 6 or mission_record_status = 12) and mission_uuid=?1",nativeQuery = true)
    int getUnFinishCountByMissionUuid(String missionUuid);

    /**
     * 获取撮合总人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where mission_record_status >= ?1",nativeQuery = true)
    int getGobetweenPersons(int missionRecordStatus);


    /**
     * 获取撮合总人数
     * @return
     */
    @Query(value = "select  count(*) from mission_record where mission_record_status >= ?2 and mission_uuid=?1",nativeQuery = true)
    int getshureJoinPersons(String missionUuid,int missionRecordStatus);

    /**
     * 获取撮合总金额
     * @return
     */
    @Query(value = "SELECT sum(mission.mission_price) totalPrice FROM mission_record INNER JOIN mission ON mission_record.mission_uuid = mission.uuid WHERE\tmission_record_status >= ?1",nativeQuery = true)
    BigDecimal getGobetweenPrice(int missionRecordStatus);



    /**
     * 按区划统计撮合数量
     * @return
     */
    @Query(value = "SELECT region_code.city_code,region_code.city_name,sum(mission.mission_price) AS total_price,count(mission_record.sn) AS person_count FROM mission_record\tINNER JOIN employee\tON mission_record.employee_uuid = employee.uuid\tINNER JOIN region_code\tON \t\temployee.region_code_uuid = region_code.uuid\tINNER JOIN\tmission\tON \t\tmission_record.mission_uuid = mission.uuid\n" +
            "WHERE\tmission_record.mission_record_status >= 7 and mission_record.mission_record_status <> 9 GROUP BY\tregion_code.city_code ORDER BY\tregion_code.city_code ASC",nativeQuery = true)
    Object[] getGoAreaPrice();



    /**
     * 按时间统计撮合人数 按日统计
     * @return
     */
    @Query(value = "select b.*,count(*) person_count from (select date_format(update_time,'%Y-%m-%d') dd,employee_uuid  from mission_record where update_time >=?1 and update_time <=?2 and mission_record_status >= 5) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoByTime(String startTime,String endTime);

    /**
     * 按时间统计撮合人数 按月统计
     * @return
     */
    @Query(value = "select b.*,count(*) person_count from (select date_format(update_time,'%Y-%m') dd,employee_uuid  from mission_record where update_time >=?1 and update_time <=?2 and mission_record_status >= 5) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoByTimeMonth(String startTime,String endTime);

    /**
     * 按时间统计撮合人数 按季度统计
     * @return
     */
    @Query(value = "select b.*,count(*) person_count from (select CONCAT(CONCAT(YEAR(update_time),CONCAT('年第',QUARTER(update_time))),'季度') dd,employee_uuid  from mission_record where update_time >=?1 and update_time <=?2 and mission_record_status >= 5) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoByTimeSeason(String startTime,String endTime);


    /**
     * 按时间统计撮合金额 按日统计
     * @return
     */
    @Query(value = "select b.dd,sum(b.mission_price) from (select date_format(mission_record.update_time,'%Y-%m-%d') dd,mission.mission_price  from mission_record inner join mission on mission_record.mission_uuid = mission.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoMoneyByTime(String startTime,String endTime);


    /**
     * 按时间统计撮合金额 按月统计
     * @return
     */
    @Query(value = "select b.dd,sum(b.mission_price) from (select date_format(mission_record.update_time,'%Y-%m') dd,mission.mission_price  from mission_record inner join mission on mission_record.mission_uuid = mission.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoMoneyByTimeMonth(String startTime,String endTime);


    /**
     * 按时间统计撮合金额 按季度统计
     * @return
     */
    @Query(value = "select b.dd,sum(b.mission_price) from (select CONCAT(CONCAT(YEAR(mission_record.update_time),CONCAT('年第',QUARTER(mission_record.update_time))),'季度') dd,mission.mission_price  from mission_record inner join mission on mission_record.mission_uuid = mission.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoMoneyByTimeSeason(String startTime,String endTime);


    /**
     * 按时间统计撮合企业 按日统计
     * @return
     */
    @Query(value = "select b.dd,count(*) from (select date_format(mission_record.update_time,'%Y-%m-%d') dd from mission_record where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7 and (employer_type = 3 or employer_type = 2)) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoEnterpriseByTime(String startTime,String endTime);

    /**
     * 按时间统计撮合企业 按月统计
     * @return
     */
    @Query(value = "select b.dd,count(*) from (select date_format(mission_record.update_time,'%Y-%m') dd from mission_record where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7 and (employer_type = 3 or employer_type = 2)) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoEnterpriseByTimeMonth(String startTime,String endTime);

    /**
     * 按时间统计撮合企业 按季度统计
     * @return
     */
    @Query(value = "select b.dd,count(*) from (select CONCAT(CONCAT(YEAR(mission_record.update_time),CONCAT('年第',QUARTER(mission_record.update_time))),'季度') dd from mission_record where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7 and (employer_type = 3 or employer_type = 2)) b GROUP BY b.dd",nativeQuery = true)
    Object[] getGoEnterpriseByTimeSeason(String startTime,String endTime);


    /**
     * 工种占比
     * @return
     */
    @Query(value = "select b.type_name,count(*) type_count from (select employment_type.type_name,employment_type.uuid as employment_type_uuid  from mission_record inner join mission on mission_record.mission_uuid = mission.uuid inner join employment_type on mission.employment_type_uuid = employment_type.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7) b GROUP BY b.employment_type_uuid",nativeQuery = true)
    Object[] getGoEmType(String startTime,String endTime);


    /**
     * 企业撮合数量前几名排行
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    @Query(value = "select c.* from (select count(*) order_num,b.employer_name from (select date_format(mission_record.update_time,'%Y-%m-%d') dd,CONCAT(IFNULL(enterprise_info.enterprise_short_name,''),IFNULL(individual_employers.individual_short_name,'')) as employer_name,CONCAT(IFNULL(enterprise_info.uuid,''),IFNULL(individual_employers.uuid,'')) as employer_uuid from mission_record left join enterprise_info on mission_record.employer_uuid = enterprise_info.uuid left join individual_employers on mission_record.employer_uuid = individual_employers.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7 and (employer_type = 3 or employer_type = 2)) b GROUP BY b.employer_uuid) c ORDER BY c.order_num desc LIMIT 0,?3",nativeQuery = true)
    Object[] getGoEmTopNum(String startTime,String endTime,int topNum);



    /**
     * 企业撮合金额前几名排行
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    @Query(value = "select c.* from (select sum(b.mission_price) order_price,b.employer_name from (select date_format(mission_record.update_time,'%Y-%m-%d') dd,mission.mission_price,CONCAT(IFNULL(enterprise_info.enterprise_short_name,''),IFNULL(individual_employers.individual_short_name,'')) as employer_name,CONCAT(IFNULL(enterprise_info.uuid,''),IFNULL(individual_employers.uuid,'')) as employer_uuid from mission_record left join enterprise_info on mission_record.employer_uuid = enterprise_info.uuid left join individual_employers on mission_record.employer_uuid = individual_employers.uuid inner join mission on mission_record.mission_uuid = mission.uuid where mission_record.update_time >=?1 and mission_record.update_time <=?2 and mission_record_status >= 7 and (mission_record.employer_type = 3 or mission_record.employer_type = 2)) b GROUP BY b.employer_uuid) c ORDER BY c.order_price desc LIMIT 0,?3",nativeQuery = true)
    Object[] getGoEmTopMoney(String startTime,String endTime,int topNum);


    /**
     * 查询简历投放总次数
     * @param custAccountUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record inner JOIN employee ON mission_record.employee_uuid = employee.uuid inner JOIN cust_account ON employee.cust_account_uuid = cust_account.uuid WHERE mission_record.active = 1 and cust_account.uuid = ?1",nativeQuery = true)
    int getTotalPush(String custAccountUuid);

    /**
     * 查询简历投放录用次数
     * @param custAccountUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record inner JOIN employee ON mission_record.employee_uuid = employee.uuid inner JOIN cust_account ON employee.cust_account_uuid = cust_account.uuid WHERE mission_record.active = 1 and mission_record.mission_record_status >=5 and cust_account.uuid = ?1",nativeQuery = true)
    int getTotalShure(String custAccountUuid);

    /**
     * 查询简历投放未录用次数
     * @param custAccountUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record inner JOIN employee ON mission_record.employee_uuid = employee.uuid inner JOIN cust_account ON employee.cust_account_uuid = cust_account.uuid WHERE mission_record.active = 1 and mission_record.mission_record_status =4 and cust_account.uuid = ?1",nativeQuery = true)
    int getTotalNotShure(String custAccountUuid);


    /**
     * 查询企业简历投放总次数
     * @param enterpriseUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record INNER JOIN mission ON mission_record.mission_uuid = mission.uuid where mission_record.active = 1 and mission.enterprise_uuid=?1",nativeQuery = true)
    int getEntTotalPush(String enterpriseUuid);

    /**
     * 查询企业简历投放录用次数
     * @param enterpriseUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record INNER JOIN mission ON mission_record.mission_uuid = mission.uuid where mission_record.active = 1 and mission_record.mission_record_status in (5,6,7,8,10,11,12) and mission.enterprise_uuid=?1",nativeQuery = true)
    int getEntTotalShure(String enterpriseUuid);

    /**
     * 查询企业简历投放未录用次数
     * @param enterpriseUuid
     * @return
     */
    @Query(value = "SELECT count(*) FROM mission_record INNER JOIN mission ON mission_record.mission_uuid = mission.uuid where mission_record.active = 1 and mission_record.mission_record_status in (0,1,2,3,4,9) and mission.enterprise_uuid=?1",nativeQuery = true)
    int getEntTotalNotShure(String enterpriseUuid);
}
