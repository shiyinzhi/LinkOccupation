package com.relyme.linkOccupation.service.common.industryinfo.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 行业信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "industry_info",indexes = {
        @Index(columnList = "uuid,industry_no,industry_name")
})
public class IndustryInfo extends BaseEntityForMysql {

    /**
     * 行业编号
     */
    @Column(name = "industry_no")
    private int industryNo;

    /**
     * 行业名称
     */
    @Column(name = "industry_name",length = 30)
    private String industryName;

    /**
     * 父级编号
     */
    @Column(name = "parent_no")
    private int parentNo;


    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getIndustryNo() {
        return industryNo;
    }

    public void setIndustryNo(int industryNo) {
        this.industryNo = industryNo;
    }

    public int getParentNo() {
        return parentNo;
    }

    public void setParentNo(int parentNo) {
        this.parentNo = parentNo;
    }
}
