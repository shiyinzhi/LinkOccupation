package com.relyme.linkOccupation.service.custaccount.dao;


import com.relyme.linkOccupation.service.custaccount.domain.RefeerCustAccountView;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RefeerCustAccountViewDao extends ExtJpaRepository<RefeerCustAccountView, String>, JpaSpecificationExecutor<RefeerCustAccountView> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    RefeerCustAccountView findByUuid(String uuid);


    /**
     * 根据时间段统计成本列表
     * @param pageable 使用占位符在native 的情况下使用分页
     * @return
     */
    @Query(value = " select count(0) AS count_m,reffer_cust_account.uuid,reffer_cust_account.mobile AS mobile,reffer_cust_account.name,cust_account.add_time AS cust_add_time from (cust_account join cust_account reffer_cust_account on((cust_account.referrer_uuid = reffer_cust_account.uuid)))  \n" +
            "  where cust_account.add_time >= ?1 and cust_account.add_time<= ?2" +
            "  group by uuid \n#pageable\n",
            countQuery = "select count(*)  from (cust_account join cust_account reffer_cust_account on((cust_account.referrer_uuid = reffer_cust_account.uuid)))  \n" +
                    " where cust_account.add_time >= ?1 and cust_account.add_time<= ?2" +
                    " group by uuid \n#pageable\n",nativeQuery = true)
    Page<Object[]> getRefferCustList(String startTime, String endTime,Pageable pageable);


    /**
     * 根据时间段统计成本列表
     * @param pageable 使用占位符在native 的情况下使用分页
     * @return
     */
    @Query(value = " select count(0) AS count,reffer_cust_account.uuid,reffer_cust_account.mobile AS mobile,reffer_cust_account.name,cust_account.add_time AS cust_add_time from (cust_account join cust_account reffer_cust_account on((cust_account.referrer_uuid = reffer_cust_account.uuid)))  \n" +
            "  where cust_account.add_time >= ?2 and cust_account.add_time<= ?3 and reffer_cust_account.mobile like ?4\n" +
            " group by uuid order by ?#{#pageable}",
            countQuery = " select count(*) AS count,reffer_cust_account.uuid,reffer_cust_account.mobile AS mobile,reffer_cust_account.name,cust_account.add_time AS cust_add_time from (cust_account join cust_account reffer_cust_account on((cust_account.referrer_uuid = reffer_cust_account.uuid)))  \n" +
                    "  where cust_account.add_time >= ?2 and cust_account.add_time<= ?3 and reffer_cust_account.mobile like ?4\n" +
                    " group by uuid \n#pageable\n",nativeQuery = true)
    Page<Object[]> getRefferCustListMobile(Pageable pageable,String startTime,String endTime,String mobile);
}
