package com.relyme.linkOccupation.service.employee.dao;


import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeDao extends ExtJpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Employee findByUuid(String uuid);

    /**
     * 通过cust uuid 查询雇员信息
     * @param custAccountUuid
     * @return
     */
    List<Employee> findByCustAccountUuid(String custAccountUuid);
}
