package com.relyme.linkOccupation.service.custaccount.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.service.Individual_employers.domain.IndividualEmployers;
import com.relyme.linkOccupation.service.employee.domain.Employee;
import com.relyme.linkOccupation.service.enterpriseinfo.domain.EnterpriseInfo;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 登陆信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "view_reffer_list_count",indexes = {
        @Index(columnList = "name")
})
public class RefeerCustAccountView {

    /**
     * 唯一ID
     */
    @Column(name = "uuid", length = 36)
    private String uuid = UUID.randomUUID().toString();

    /**
     * 推广人数
     */
    @Column(name = "count",length = 15)
    private int count;

    /**
     * 手机号码
     */
    @Id
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 姓名
     */
    @Column(name = "name",length = 128)
    private String name;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "add_time",updatable = false)
    private Date custAddTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCustAddTime() {
        return custAddTime;
    }

    public void setCustAddTime(Date custAddTime) {
        this.custAddTime = custAddTime;
    }
}
