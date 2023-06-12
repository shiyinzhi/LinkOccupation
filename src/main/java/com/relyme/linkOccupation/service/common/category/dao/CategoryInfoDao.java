package com.relyme.linkOccupation.service.common.category.dao;


import com.relyme.linkOccupation.service.common.category.domain.CategoryInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryInfoDao extends ExtJpaRepository<CategoryInfo, String>, JpaSpecificationExecutor<CategoryInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    CategoryInfo findByUuid(String uuid);


    /**
     * 通过名称查询职工类别信息
     * @param categoryName
     * @return
     */
    CategoryInfo findByCategoryNameAndEnterpriseUuid(String categoryName,String enterpriseUuid);
}
