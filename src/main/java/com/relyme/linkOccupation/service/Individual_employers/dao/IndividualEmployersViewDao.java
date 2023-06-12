package com.relyme.linkOccupation.service.Individual_employers.dao;


import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployersView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndividualEmployersViewDao extends ExtJpaRepository<IndividualEmployersView, String>, JpaSpecificationExecutor<IndividualEmployersView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    IndividualEmployersView findByUuid(String uuid);
}
