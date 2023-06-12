package com.relyme.linkOccupation.service.department.dao;


import com.relyme.linkOccupation.service.department.domain.Post;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostDao extends ExtJpaRepository<Post, String>, JpaSpecificationExecutor<Post> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    Post findByUuid(String uuid);

}
