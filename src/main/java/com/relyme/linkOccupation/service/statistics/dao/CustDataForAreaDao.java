package com.relyme.linkOccupation.service.statistics.dao;


import com.relyme.linkOccupation.service.statistics.domain.CustDataForArea;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustDataForAreaDao extends ExtJpaRepository<CustDataForArea, String>, JpaSpecificationExecutor<CustDataForArea> {


}
