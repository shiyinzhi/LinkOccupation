package com.relyme.linkOccupation.service.sensitive_word.dao;


import com.relyme.linkOccupation.service.sensitive_word.domain.SensitiveWord;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SensitiveWordDao extends ExtJpaRepository<SensitiveWord, String>, JpaSpecificationExecutor<SensitiveWord> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    SensitiveWord findByUuid(String uuid);

    List<SensitiveWord> findByActive(int active);

}
