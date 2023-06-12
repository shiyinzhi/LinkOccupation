package com.relyme.linkOccupation.service.employee.dao;


import com.relyme.linkOccupation.service.employee.domain.EmployeeType;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeTypeDao extends ExtJpaRepository<EmployeeType, String>, JpaSpecificationExecutor<EmployeeType> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    EmployeeType findByUuid(String uuid);

    /**
     * 通过雇员类型名称查询雇员类型
     * @param employeeTypeName
     * @return
     */
    EmployeeType findByEmployeeTypeName(String employeeTypeName);
}
