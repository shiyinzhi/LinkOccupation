package com.relyme.linkOccupation.service.common.gettype.dao;


import com.relyme.linkOccupation.service.common.gettype.domain.GetTypeInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GetTypeInfoDao extends ExtJpaRepository<GetTypeInfo, String>, JpaSpecificationExecutor<GetTypeInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    GetTypeInfo findByUuid(String uuid);

    /**
     * 通过名字获取获取方式信息
     * @param getTypeName
     * @return
     */
    GetTypeInfo findByGetTypeName(String getTypeName);

}
