package com.relyme.linkOccupation.service.statistics.dao;


import com.relyme.linkOccupation.service.statistics.domain.CustDataConfig;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustDataConfigDao extends ExtJpaRepository<CustDataConfig, String>, JpaSpecificationExecutor<CustDataConfig> {


}
