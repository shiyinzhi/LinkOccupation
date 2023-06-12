package com.relyme.linkOccupation.service.custaccount.domain;


import javax.persistence.Id;
import java.math.BigInteger;

/**
 * 登陆信息
 * @author shiyinzhi
 */
public class RefeerCustAccountViewNew {

    /**
     * 唯一ID
     */
    private String uuid;

    /**
     * 推广人数
     */
    private BigInteger count;

    /**
     * 手机号码
     */
    @Id
    private String mobile;

    /**
     * 姓名
     */
    private String name;


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

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
