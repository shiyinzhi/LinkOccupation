package com.relyme.linkOccupation.service.department.dao;


import com.relyme.linkOccupation.service.department.domain.Department;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentDao extends ExtJpaRepository<Department, String>, JpaSpecificationExecutor<Department> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Department findByUuid(String uuid);

    /**
     * 通过名字查询
     * @param departmentName
     * @return
     */
    Department findByDepartmentNameAndEnterpriseUuid(String departmentName,String enterpriseUuid);

}
