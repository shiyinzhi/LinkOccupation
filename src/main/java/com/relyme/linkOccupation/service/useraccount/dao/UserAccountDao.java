package com.relyme.linkOccupation.service.useraccount.dao;


import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountDao extends ExtJpaRepository<UserAccount, String>, JpaSpecificationExecutor<UserAccount> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    UserAccount findByUuid(String uuid);

    /**
     * 根据手机号码和密码查询患者账户信息
     * @param mobile
     * @param pwd
     * @return
     */
    UserAccount findByMobileAndPwd(String mobile, String pwd);


    /**
     * 根据手机号查询患者账户信息
     * @param mobile
     * @return
     */
    UserAccount findByMobile(String mobile);


    /**
     * 查询后台用户数量
     * @return
     */
    @Query(value = "select count(*) from user_account where admin=0 and active = 1",nativeQuery = true)
    int findUserAccountCount();

    /**
     * 查询后台园区用户数量
     * @return
     */
    @Query(value = "select count(*) from user_account where admin=0 and role_id = 1 and active = 1",nativeQuery = true)
    int findUserAccountYQCount();


    /**
     * 查询后台用户数量 本月新增
     * @return
     */
    @Query(value = "select count(*) from user_account where admin=0 and active = 1 and add_time >=?1 and add_time <=?2",nativeQuery = true)
    int findUserAccountThisMonthCount(String startTime,String endTime);


    /**
     * 查询后台园区用户数量 本月新增
     * @return
     */
    @Query(value = "select count(*) from user_account where admin=0 and role_id = 1 and active = 1 and add_time >=?1 and add_time <=?2",nativeQuery = true)
    int findUserAccountYQThisMonthCount(String startTime,String endTime);

}
