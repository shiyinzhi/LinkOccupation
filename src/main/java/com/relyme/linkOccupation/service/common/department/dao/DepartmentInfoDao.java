package com.relyme.linkOccupation.service.common.department.dao;


import com.relyme.linkOccupation.service.common.department.domain.DepartmentInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentInfoDao extends ExtJpaRepository<DepartmentInfo, String>, JpaSpecificationExecutor<DepartmentInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    DepartmentInfo findByUuid(String uuid);


    /**
     * 通过名字获取部门信息
     * @param departmentName
     * @return
     */
    DepartmentInfo findByDepartmentNameAndEnterpriseUuid(String departmentName,String enterpriseUuid);

    /**
     * 通过uuid和公司uuid 查询部门信息
     * @param uuid
     * @param enterpriseUuid
     * @return
     */
    DepartmentInfo findByUuidAndEnterpriseUuid(String uuid,String enterpriseUuid);

}
