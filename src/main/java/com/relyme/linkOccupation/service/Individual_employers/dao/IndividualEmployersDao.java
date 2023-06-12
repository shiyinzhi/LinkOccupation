package com.relyme.linkOccupation.service.Individual_employers.dao;


import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndividualEmployersDao extends ExtJpaRepository<IndividualEmployers, String>, JpaSpecificationExecutor<IndividualEmployers> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    IndividualEmployers findByUuid(String uuid);

    /**
     * 通过custAccountUuid 查询个人雇主信息
     * @param custAccountUuid
     * @return
     */
    List<IndividualEmployers> findByCustAccountUuid(String custAccountUuid);


    /**
     * 通过名称或简称查询
     * @param individualName
     * @param individualShortName
     * @return
     */
    @Query(value = "select  count(*) from individual_employers where individual_name=?1 or individual_short_name=?2",nativeQuery = true)
    int getInCount(String individualName,String individualShortName);
}
