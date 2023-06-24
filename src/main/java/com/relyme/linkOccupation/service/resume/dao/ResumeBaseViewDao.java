package com.relyme.linkOccupation.service.resume.dao;


import com.relyme.linkOccupation.service.resume.domain.ResumeBaseView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResumeBaseViewDao extends ExtJpaRepository<ResumeBaseView, String>, JpaSpecificationExecutor<ResumeBaseView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ResumeBaseView findByUuid(String uuid);


    /**
     * 通过用户uuid 查询信息
     * @param custAccountUuid
     * @return
     */
    ResumeBaseView findByCustAccountUuid(String custAccountUuid);
}
