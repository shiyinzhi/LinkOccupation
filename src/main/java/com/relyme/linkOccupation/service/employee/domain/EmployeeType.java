package com.relyme.linkOccupation.service.employee.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 雇员分类信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "employee_type",indexes = {
        @Index(columnList = "uuid,employee_type_name")
})
public class EmployeeType extends BaseEntityForMysql {


    /**
     * 名称
     */
    @Column(name = "employee_type_name",length = 150)
    private String employeeTypeName;

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }
}
