package com.relyme.linkOccupation.service.common.post.dao;


import com.relyme.linkOccupation.service.common.post.domain.PostInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostInfoDao extends ExtJpaRepository<PostInfo, String>, JpaSpecificationExecutor<PostInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    PostInfo findByUuid(String uuid);

    /**
     * 通过岗位编号查询岗位信息
     * @param postNo
     * @return
     */
    PostInfo findByPostNo(Integer postNo);

    /**
     * 通过岗位名称查询岗位信息
     * @param postName
     * @return
     */
    PostInfo findByPostNameAndEnterpriseUuid(String postName,String enterpriseUuid);
}
