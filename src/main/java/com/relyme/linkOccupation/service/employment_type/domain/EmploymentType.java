package com.relyme.linkOccupation.service.employment_type.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.*;

/**
 * 用工分类信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "employment_type",indexes = {
        @Index(columnList = "uuid,user_account_uuid")
})
public class EmploymentType extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 分类名称
     */
    @Column(name = "type_name",length = 128,nullable = false)
    private String typeName;

    /**
     * icon名称
     */
    @Column(name = "type_icon",length = 128)
    private String typeIcon;

    @Transient
    private String typeIconPath;

    /**
     * 类型  1.常规 2.临时工 3.长期工
     */
    @Column(name = "type_ocname",length = 11,columnDefinition="tinyint default 1")
    private Integer typeOcname;

    /**
     * 排序
     */
    @Column(name = "type_order",length = 11,columnDefinition="tinyint default 0")
    private Integer typeOrder;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public Integer getTypeOcname() {
        return typeOcname;
    }

    public void setTypeOcname(Integer typeOcname) {
        this.typeOcname = typeOcname;
    }

    public String getTypeIconPath() {
        return typeIconPath;
    }

    public void setTypeIconPath(String typeIconPath) {
        this.typeIconPath = typeIconPath;
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }
}
