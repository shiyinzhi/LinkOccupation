package com.relyme.linkOccupation.service.train.dao;


import com.relyme.linkOccupation.service.train.domain.TrainRecordInfo;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface TrainRecordInfoDao extends ExtJpaRepository<TrainRecordInfo, String>, JpaSpecificationExecutor<TrainRecordInfo> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    TrainRecordInfo findByUuid(String uuid);


    /**
     * 通过身份证号和培训日期查询培训信息
     * @param identityCardNo
     * @param trainingData
     * @return
     */
    TrainRecordInfo findByIdentityCardNoAndTrainingData(String identityCardNo, Date trainingData);
}
