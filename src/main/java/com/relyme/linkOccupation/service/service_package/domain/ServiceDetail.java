package com.relyme.linkOccupation.service.service_package.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 服务内容信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "service_detail",indexes = {
        @Index(columnList = "uuid,user_account_uuid,service_package_uuid")
})
public class ServiceDetail extends BaseEntityForMysql {

    /**
     * 创建人uuid
     */
    @Column(name = "user_account_uuid", length = 36)
    private String userAccountUuid;

    /**
     * 服务套餐uuid
     */
    @Column(name = "service_package_uuid", length = 36)
    private String servicePackageUuid;

    /**
     * 内容描述
     */
    @Column(name = "service_content",length = 512)
    private String serviceContent;

    /**
     * 服务次数
     */
    @Column(name = "service_count", length = 3,columnDefinition="int default 0")
    private int serviceCount;

    /**
     * 服务有效期 可以是年或月份，具体为数字，以用户购买的选择单位为准
     */
    @Column(name = "service_limit", length = 3,columnDefinition="int default 0")
    private int serviceLimit;

    /**
     * 服务内容使用类型 0不适用 1按次数使用 2按进度使用
     */
    @Column(name = "service_use_type", length = 3,columnDefinition="int default 0")
    private int serviceUseType;

//    /**
//     * 开始时间
//     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @Column(name = "start_time")
//    private Date startTime;
//
//    /**
//     * 结束时间
//     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    @Column(name = "end_time")
//    private Date endTime;

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getServiceLimit() {
        return serviceLimit;
    }

    public void setServiceLimit(int serviceLimit) {
        this.serviceLimit = serviceLimit;
    }

//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }

    public String getServicePackageUuid() {
        return servicePackageUuid;
    }

    public void setServicePackageUuid(String servicePackageUuid) {
        this.servicePackageUuid = servicePackageUuid;
    }

    public int getServiceUseType() {
        return serviceUseType;
    }

    public void setServiceUseType(int serviceUseType) {
        this.serviceUseType = serviceUseType;
    }
}
