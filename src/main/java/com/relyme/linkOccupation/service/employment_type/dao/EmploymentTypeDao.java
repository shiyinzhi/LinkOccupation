package com.relyme.linkOccupation.service.employment_type.dao;


import com.relyme.linkOccupation.service.employment_type.domain.EmploymentType;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmploymentTypeDao extends ExtJpaRepository<EmploymentType, String>, JpaSpecificationExecutor<EmploymentType> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EmploymentType findByUuid(String uuid);

}
