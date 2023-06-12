package com.relyme.linkOccupation.service.custaccount.dao;


import com.relyme.linkOccupation.service.custaccount.domain.CustAccountBlackListView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustAccountBlackListDao extends ExtJpaRepository<CustAccountBlackListView, String>, JpaSpecificationExecutor<CustAccountBlackListView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    CustAccountBlackListView findByUuid(String uuid);


}
