package com.relyme.linkOccupation.service.common.post.domain;


import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 工作岗位信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "post_info",indexes = {
        @Index(columnList = "uuid,post_no,post_name")
})
public class PostInfo extends BaseEntityForMysql {

    /**
     * 工作岗位名称
     */
    @Column(name = "post_no")
    private Integer postNo;

    /**
     * 工作岗位名称
     */
    @Column(name = "post_name",length = 30)
    private String postName;

    /**
     * 父级岗位编号
     */
    @Column(name = "parent_no")
    private Integer parentNo;

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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public Integer getParentNo() {
        return parentNo;
    }

    public void setParentNo(Integer parentNo) {
        this.parentNo = parentNo;
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
