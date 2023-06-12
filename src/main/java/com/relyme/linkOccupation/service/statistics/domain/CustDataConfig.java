package com.relyme.linkOccupation.service.statistics.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 首页数据配置
 * @author shiyinzhi
 */
@Entity
@Table(name = "cust_data_config",indexes = {
        @Index(columnList = "uuid")
})
public class CustDataConfig extends BaseEntityForMysql {

    /**
     * 是否使用配置数据
     */
    @Column(name = "cust_data_active", length = 3,columnDefinition="tinyint default 0")
    private int custDataActive ;

    public int getCustDataActive() {
        return custDataActive;
    }

    public void setCustDataActive(int custDataActive) {
        this.custDataActive = custDataActive;
    }
}
