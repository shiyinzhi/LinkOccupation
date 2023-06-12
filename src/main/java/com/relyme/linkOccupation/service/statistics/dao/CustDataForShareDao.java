package com.relyme.linkOccupation.service.statistics.dao;


import com.relyme.linkOccupation.service.statistics.domain.CustDataForShare;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustDataForShareDao extends ExtJpaRepository<CustDataForShare, String>, JpaSpecificationExecutor<CustDataForShare> {


}
