package com.relyme.linkOccupation.service.common.category.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 职工类别信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "category_info",indexes = {
        @Index(columnList = "uuid,category_name")
})
public class CategoryInfo extends BaseEntityForMysql {


    /**
     * 职工类别名称
     */
    @Column(name = "category_name",length = 150)
    private String categoryName;

    /**
     * 企业信息
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 提交人/确认人
     */
    @Column(name = "user_account_uuid",length = 36)
    private String userAccountUuid;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
