package com.relyme.linkOccupation.service.statistics.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 推广数据配置
 * @author shiyinzhi
 */
@Entity
@Table(name = "cust_data_for_share",indexes = {
        @Index(columnList = "uuid")
})
public class CustDataForShare extends BaseEntityForMysql {

    /**
     * 分享人数
     */
    @Column(name = "share_users",length = 15)
    int shareUsers;


    /**
     * 注册人数
     */
    @Column(name = "share_register_users",length = 15)
    int shareRegisterUsers;


    public int getShareUsers() {
        return shareUsers;
    }

    public void setShareUsers(int shareUsers) {
        this.shareUsers = shareUsers;
    }

    public int getShareRegisterUsers() {
        return shareRegisterUsers;
    }

    public void setShareRegisterUsers(int shareRegisterUsers) {
        this.shareRegisterUsers = shareRegisterUsers;
    }
}
