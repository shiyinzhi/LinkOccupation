package com.relyme.linkOccupation.service.statistics.dao;


import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsDao extends ExtJpaRepository<CustAccount, String>, JpaSpecificationExecutor<CustAccount> {

    /**
     * 统计雇员新增数据 每日增加人数 日期  总共人数
     * @return
     */
    @Query(value = "select count(*) num,DATE_FORMAT(add_time,'%Y-%m-%d') dd,(select count(*) from employee where add_time <=CONCAT(dd,' 23:59:59') and add_time >=?1 and add_time <=?2) total_num from employee where add_time >=?1 and add_time <=?2 GROUP BY dd order by dd",nativeQuery = true)
    Object[] getEmployeeNums(String startTime, String endTime);


    /**
     * 统计个人雇主新增数据 每日新增人数 日期 总共人数
     * @return
     */
    @Query(value = "select count(*) num,DATE_FORMAT(add_time,'%Y-%m-%d') dd,(select count(*) from individual_employers where add_time <=CONCAT(dd,' 23:59:59') and add_time >=?1 and add_time <=?2) total_num from individual_employers where add_time >=?1 and add_time <=?2 GROUP BY dd order by dd",nativeQuery = true)
    Object[] getIndividualEmployersNums(String startTime, String endTime);


    /**
     * 统计企业雇主新增数据 每日新增人数 日期 总共人数
     * @return
     */
    @Query(value = "select count(*) num,DATE_FORMAT(add_time,'%Y-%m-%d') dd,(select count(*) from enterprise_info where add_time <=CONCAT(dd,' 23:59:59') and add_time >=?1 and add_time <=?2) total_num from enterprise_info where  add_time >=?1 and add_time <=?2 GROUP BY dd order by dd",nativeQuery = true)
    Object[] getEnterpriseInfoNums(String startTime, String endTime);
}
