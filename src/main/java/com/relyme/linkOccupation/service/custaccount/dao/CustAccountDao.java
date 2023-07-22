package com.relyme.linkOccupation.service.custaccount.dao;


import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustAccountDao extends ExtJpaRepository<CustAccount, String>, JpaSpecificationExecutor<CustAccount> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    CustAccount findByUuid(String uuid);

    /**
     * 通过sn查询信息
     * @param sn
     * @return
     */
    CustAccount findBySn(long sn);

    /**
     * 通过openid 查询客户信息
     * @param openId
     * @return
     */
    CustAccount findByOpenid(String openId);

    /**
     * 通过openid 和手机号查询账户信息
     * @param openId
     * @param mobile
     * @return
     */
    CustAccount findByOpenidAndMobile(String openId,String mobile);


    /**
     * 根据手机号查询患者账户信息
     * @param mobile
     * @return
     */
    CustAccount findByMobile(String mobile);

    /**
     * 通过手机号集合查询信息
     * @param mobiles
     * @return
     */
    List<CustAccount> findByMobileIsIn(List<String> mobiles);


    /**
     *
     * @return
     */
    List<CustAccount> findAllByOrderByIntegralDesc();

    /**
     * 通过uuid 集合查询
     * @param uuids
     * @return
     */
    List<CustAccount> findByUuidIn(List<String> uuids);


    /**
     * 通过名字查询患者账户信息
     * @param name
     * @return
     */
    List<CustAccount> findByName(String name);


    /**
     * 查询小程序用户数量
     * @return
     */
    @Query(value = "select count(*) from cust_account where active = 1",nativeQuery = true)
    int findCustAccountCount();


    /**
     * 查询小程序用户数量 本月新增
     * @return
     */
    @Query(value = "select count(*) from cust_account where active = 1 and add_time >=?1 and add_time <=?2",nativeQuery = true)
    int findCustAccountThisMonthCount(String startTime,String endTime);

    /**
     * 查询小程序用户数量 分享注册人数
     * @return
     */
    @Query(value = "select count(*) from cust_account where referrer_uuid is not null",nativeQuery = true)
    int findCustAccountCountShare();

}
