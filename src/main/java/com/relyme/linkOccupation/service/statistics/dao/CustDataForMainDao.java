package com.relyme.linkOccupation.service.statistics.dao;


import com.relyme.linkOccupation.service.statistics.domain.CustDataForMain;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustDataForMainDao extends ExtJpaRepository<CustDataForMain, String>, JpaSpecificationExecutor<CustDataForMain> {


}
