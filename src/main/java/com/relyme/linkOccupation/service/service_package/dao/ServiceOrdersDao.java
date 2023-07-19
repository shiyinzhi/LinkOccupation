package com.relyme.linkOccupation.service.service_package.dao;


import com.relyme.linkOccupation.service.service_package.domain.ServiceOrders;
import com.relyme.linkOccupation.utils.dao.ExtJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ServiceOrdersDao extends ExtJpaRepository<ServiceOrders, String>, JpaSpecificationExecutor<ServiceOrders> {

    /**
     * 通过uuid查询信息
     * @param uuid
     * @return
     */
    ServiceOrders findByUuid(String uuid);


    /**
     * 查询企业购买体验包的次数
     * @param enterpriseUuid
     * @return
     */
    @Query(value = "SELECT\n" +
            "\tcount(*)\n" +
            "FROM\n" +
            "\tservice_orders\n" +
            "\tINNER JOIN\n" +
            "\tservice_special_offer\n" +
            "\tON \n" +
            "\t\tservice_orders.service_special_offer_uuid = service_special_offer.uuid\n" +
            "WHERE\n" +
            "\tservice_special_offer.special_type = 0 AND\n" +
            "\tservice_orders.is_buy_offline = 1 AND\n" +
            "\tservice_orders.enterprise_uuid = ?1",nativeQuery = true)
    int getExperiencePackCount(String enterpriseUuid);


    /**
     * 通过开始和结束时间查询企业订单信息
     * @param startTime
     * @param endTime
     * @param enterpriseUuid
     * @return
     */
    ServiceOrders findByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndEnterpriseUuidAndActive(Date startTime,Date endTime,String enterpriseUuid,int active);


    /**
     * 通过企业uuid 和状态查询
     * @param enterpriseUuid
     * @param active
     * @return
     */
    ServiceOrders findByEnterpriseUuidAndActive(String enterpriseUuid,int active);

    /**
     * 更具时间段查询服务订单数量
     * @return
     */
    @Query(value = "select count(*) from service_orders where end_time >=?1 and end_time <=?2",nativeQuery = true)
    int getServiceOrderCountByTimeRange(String startTime,String endTime);


    /**
     * 获取到期的服务订单数量
     * @return
     */
    @Query(value = "select count(*) from service_orders where  end_time <=?1 and active = 1 and  is_expire = 0",nativeQuery = true)
    int getServiceOrderExpire(String endTime);

}
