package com.relyme.linkOccupation.service.employee.dao;


import com.relyme.linkOccupation.service.employee.domain.EmployeeView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeViewDao extends ExtJpaRepository<EmployeeView, String>, JpaSpecificationExecutor<EmployeeView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EmployeeView findByUuid(String uuid);
}
