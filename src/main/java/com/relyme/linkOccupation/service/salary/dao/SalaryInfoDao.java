package com.relyme.linkOccupation.service.salary.dao;


import com.relyme.linkOccupation.service.salary.domain.SalaryInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface SalaryInfoDao extends ExtJpaRepository<SalaryInfo, String>, JpaSpecificationExecutor<SalaryInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    SalaryInfo findByUuid(String uuid);

    /**
     * 通过身份证号和工资月份查询薪资信息
     * @param identityCardNo
     * @param salaryMonth
     * @return
     */
    SalaryInfo findByIdentityCardNoAndSalaryMonth(String identityCardNo, Date salaryMonth);


    /**
     * 根据时间段统计薪资和成本统计
     * @param salaryMonthStart
     * @param salaryMonthEnd
     * @return
     */
    @Query(value = "select sum(si.salary_true) AS salaryTrueTotal," +
            "sum(si.medical_insurance+si.out_of_work+si.endowment_insurance+si.housing_fund) AS insuranceTotal," +
            "sum(si.salary_true+si.medical_insurance+si.out_of_work+si.endowment_insurance+si.housing_fund) AS costTotal,si.salary_month AS salaryMonth " +
            "from salary_info AS si where si.salary_month >=?1 " +
            "and si.salary_month <=?2 GROUP BY si.salary_month",nativeQuery = true)
    Object[] getCostStatistics(Date salaryMonthStart,Date salaryMonthEnd);


    /**
     * 根据时间段统计成本列表
     * @param page
     * @param pageSize
     * @return
     */
    @Query(value = "select sum(salary_true+medical_insurance+out_of_work+endowment_insurance+housing_fund) cost_total," +
            "count(uuid),salary_month from salary_info where 1=1 GROUP BY salary_month ORDER BY salary_month desc limit ?1,?2",nativeQuery = true)
    Object[] getCostList(Integer page,Integer pageSize);

    /**
     * 根据时间段统计成本列表
     * @param pageable 使用占位符在native 的情况下使用分页
     * @return
     */
    @Query(value = "select sum(salary_true+medical_insurance+out_of_work+endowment_insurance+housing_fund)," +
            "count(uuid),salary_month,remark from salary_info where 1=1 GROUP BY salary_month order by ?#{#pageable}",
            countQuery = "select count(a.salary_month) from (select sum(salary_true+medical_insurance+out_of_work+endowment_insurance+housing_fund) cost_total,count(uuid),salary_month from salary_info where 1=1 GROUP BY salary_month) a",nativeQuery = true)
    Page<Object[]> getCostList(Pageable pageable);

}
