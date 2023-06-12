package com.relyme.linkOccupation.service.resume.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 个人简历家庭情况信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "resume_family",indexes = {
        @Index(columnList = "uuid,cust_account_uuid")
})
public class ResumeFamily extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "cust_account_uuid", length = 36)
    private String custAccountUuid;


    /**
     * 成员名称
     */
    @Column(name = "member_name",length = 128)
    private String memberName;

    /**
     * 关系
     */
    @Column(name = "member_relation",length = 128)
    private String memberRelation;

    /**
     * 联系电话
     */
    @Column(name = "contact_phone",length = 12)
    private String contactPhone;

    /**
     * 收入
     */
    @Column(name = "member_earning",length = 18,scale = 2)
    private BigDecimal memberEarning;


    public String getCustAccountUuid() {
        return custAccountUuid;
    }

    public void setCustAccountUuid(String custAccountUuid) {
        this.custAccountUuid = custAccountUuid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRelation() {
        return memberRelation;
    }

    public void setMemberRelation(String memberRelation) {
        this.memberRelation = memberRelation;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public BigDecimal getMemberEarning() {
        return memberEarning;
    }

    public void setMemberEarning(BigDecimal memberEarning) {
        this.memberEarning = memberEarning;
    }
}
