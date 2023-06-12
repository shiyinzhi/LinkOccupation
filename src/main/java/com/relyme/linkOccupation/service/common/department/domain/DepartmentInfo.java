package com.relyme.linkOccupation.service.common.department.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 部门信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "department_info",indexes = {
        @Index(columnList = "uuid,department_name")
})
public class DepartmentInfo extends BaseEntityForMysql {


    /**
     * 部门名称
     */
    @Column(name = "department_name",length = 150)
    private String departmentName;


    /**
     * 父级部门 uuid
     */
    @Column(name = "parent_department_uuid",length = 36)
    private String parentDepartmentUuid;

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public String getParentDepartmentUuid() {
        return parentDepartmentUuid;
    }

    public void setParentDepartmentUuid(String parentDepartmentUuid) {
        this.parentDepartmentUuid = parentDepartmentUuid;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }
}
