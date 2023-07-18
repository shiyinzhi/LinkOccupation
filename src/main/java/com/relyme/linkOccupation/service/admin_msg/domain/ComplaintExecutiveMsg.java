package com.relyme.linkOccupation.service.admin_msg.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 投诉建议消息接收人员信息配置
 * @author shiyinzhi
 */
@Entity
@Table(name = "complaint_executive_msg",indexes = {
        @Index(columnList = "uuid,mobile,cust_account_uuid")
})
public class ComplaintExecutiveMsg extends BaseEntityForMysql {

    /**
     * 手机号码
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 管理员uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }
}
