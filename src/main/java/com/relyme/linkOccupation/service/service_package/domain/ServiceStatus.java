package com.relyme.linkOccupation.service.service_package.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务状态信息 包含服务进度信息 如果一个服务完成了，需要生成一条新的服务记录
 * @author shiyinzhi
 */
@Entity
@Table(name = "service_status",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid,enterprise_uuid,service_detail_uuid")
})
public class ServiceStatus extends BaseEntityForMysql {

    /**
     * 企业UUID
     */
    @Column(name = "enterprise_uuid",length = 36)
    private String enterpriseUuid;

    /**
     * 处理人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 服务套餐uuid
     */
    @Column(name = "service_package_uuid", length = 36)
    private String servicePackageUuid;

    /**
     * 服务详情uuid
     */
    @Column(name = "service_detail_uuid", length = 36)
    private String serviceDetailUuid;

    /**
     * 内容描述
     */
    @Column(name = "service_content",length = 512)
    private String serviceContent;

    /**
     * 服务时间月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "service_time")
    private Date serviceTime;


    /**
     * 服务进度 百分比
     */
    @Column(name = "status_process", scale = 2,length = 18)
    private BigDecimal statusProcess;

    /**
     * 剩余服务次数
     */
    @Column(name = "service_count", length = 3,columnDefinition="int default 0")
    private int serviceCount;

    /**
     * 已使用服务次数
     */
    @Column(name = "service_count_used", length = 3,columnDefinition="int default 0")
    private int serviceCountUsed;

    /**
     * 是否已完成 0否 1是
     */
    @Column(name = "has_finished", length = 3,columnDefinition="tinyint default 0")
    private int hasFinished;


    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public String getServiceDetailUuid() {
        return serviceDetailUuid;
    }

    public void setServiceDetailUuid(String serviceDetailUuid) {
        this.serviceDetailUuid = serviceDetailUuid;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public BigDecimal getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(BigDecimal statusProcess) {
        this.statusProcess = statusProcess;
    }

    public String getEnterpriseUuid() {
        return enterpriseUuid;
    }

    public void setEnterpriseUuid(String enterpriseUuid) {
        this.enterpriseUuid = enterpriseUuid;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(int hasFinished) {
        this.hasFinished = hasFinished;
    }

    public int getServiceCountUsed() {
        return serviceCountUsed;
    }

    public void setServiceCountUsed(int serviceCountUsed) {
        this.serviceCountUsed = serviceCountUsed;
    }
}
