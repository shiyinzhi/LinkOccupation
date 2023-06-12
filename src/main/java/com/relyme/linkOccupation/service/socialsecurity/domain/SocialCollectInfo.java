package com.relyme.linkOccupation.service.socialsecurity.domain;


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
 * 社保汇总信息
 * @author shiyinzhi
 */
@Entity
@Table(name = "social_collect_info",indexes = {
        @Index(columnList = "uuid,roster_name,identity_card_no")
})
public class SocialCollectInfo extends BaseEntityForMysql {


    /**
     * 员工编号
     */
    @Column(name = "job_number",length = 128)
    private String jobNumber;


    /**
     * 员工工号
     */
    @Column(name = "worker_number",length = 128)
    private String workerNumber;

    /**
     * 姓名
     */
    @Column(name = "roster_name",length = 128)
    private String rosterName;

    /**
     * 证件号码 身份证号码
     */
    @Column(name = "identity_card_no",length = 20)
    private String identityCardNo;

    /**
     * 部门
     */
    @Column(name = "department_uuid",length = 36)
    private String departmentUuid;

    /**
     * 社保月份
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM",timezone="GMT+8")
    @Column(name = "social_month")
    private Date socialMonth;

    /**
     * 缴费基数
     */
    @Column(name = "social_base",length = 18,scale = 2)
    private BigDecimal socialBase;

    /**
     * 工伤
     */
    @Column(name = "occupational_injury",length = 18,scale = 2)
    private BigDecimal occupationalInjury;

    /**
     * 生育
     */
    @Column(name = "maternity_insurance",length = 18,scale = 2)
    private BigDecimal maternityInsurance;

    /**
     * 养老保险个人8%
     */
    @Column(name = "endowment_insurance_self",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceSelf;

    /**
     * 养老保险企业12%
     */
    @Column(name = "endowment_insurance_ent",length = 18,scale = 2)
    private BigDecimal endowmentInsuranceEnt;

    /**
     * 基本医疗个人2%
     */
    @Column(name = "basemedical_insurance_self",length = 18,scale = 2)
    private BigDecimal basemedicalInsuranceSelf;

    /**
     * 基本医疗企业12%
     */
    @Column(name = "basemedical_insurance_ent",length = 18,scale = 2)
    private BigDecimal basemedicalInsuranceEnt;

    /**
     * 大额医疗个人2%
     */
    @Column(name = "bigmedical_insurance_self",length = 18,scale = 2)
    private BigDecimal bigmedicalInsuranceSelf;

    /**
     * 大额医疗企业7%
     */
    @Column(name = "bigmedical_insurance_ent",length = 18,scale = 2)
    private BigDecimal bigmedicalInsuranceEnt;

    /**
     * 失业保险个人0.5%
     */
    @Column(name = "out_of_work_self",length = 18,scale = 2)
    private BigDecimal outOfWorkSelf;

    /**
     * 失业保险个人0.5%
     */
    @Column(name = "out_of_work_ent",length = 18,scale = 2)
    private BigDecimal outOfWorkEnt;

    /**
     * 个人小计
     */
    @Column(name = "self_total",length = 18,scale = 2)
    private BigDecimal selfTotal;

    /**
     * 企业小计
     */
    @Column(name = "ent_total",length = 18,scale = 2)
    private BigDecimal entTotal;

    /**
     * 合计
     */
    @Column(name = "social_total",length = 18,scale = 2)
    private BigDecimal socialTotal;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public Date getSocialMonth() {
        return socialMonth;
    }

    public void setSocialMonth(Date socialMonth) {
        this.socialMonth = socialMonth;
    }

    public BigDecimal getSocialBase() {
        return socialBase;
    }

    public void setSocialBase(BigDecimal socialBase) {
        this.socialBase = socialBase;
    }

    public BigDecimal getOccupationalInjury() {
        return occupationalInjury;
    }

    public void setOccupationalInjury(BigDecimal occupationalInjury) {
        this.occupationalInjury = occupationalInjury;
    }

    public BigDecimal getMaternityInsurance() {
        return maternityInsurance;
    }

    public void setMaternityInsurance(BigDecimal maternityInsurance) {
        this.maternityInsurance = maternityInsurance;
    }

    public BigDecimal getEndowmentInsuranceSelf() {
        return endowmentInsuranceSelf;
    }

    public void setEndowmentInsuranceSelf(BigDecimal endowmentInsuranceSelf) {
        this.endowmentInsuranceSelf = endowmentInsuranceSelf;
    }

    public BigDecimal getEndowmentInsuranceEnt() {
        return endowmentInsuranceEnt;
    }

    public void setEndowmentInsuranceEnt(BigDecimal endowmentInsuranceEnt) {
        this.endowmentInsuranceEnt = endowmentInsuranceEnt;
    }

    public BigDecimal getBasemedicalInsuranceSelf() {
        return basemedicalInsuranceSelf;
    }

    public void setBasemedicalInsuranceSelf(BigDecimal basemedicalInsuranceSelf) {
        this.basemedicalInsuranceSelf = basemedicalInsuranceSelf;
    }

    public BigDecimal getBasemedicalInsuranceEnt() {
        return basemedicalInsuranceEnt;
    }

    public void setBasemedicalInsuranceEnt(BigDecimal basemedicalInsuranceEnt) {
        this.basemedicalInsuranceEnt = basemedicalInsuranceEnt;
    }

    public BigDecimal getBigmedicalInsuranceSelf() {
        return bigmedicalInsuranceSelf;
    }

    public void setBigmedicalInsuranceSelf(BigDecimal bigmedicalInsuranceSelf) {
        this.bigmedicalInsuranceSelf = bigmedicalInsuranceSelf;
    }

    public BigDecimal getBigmedicalInsuranceEnt() {
        return bigmedicalInsuranceEnt;
    }

    public void setBigmedicalInsuranceEnt(BigDecimal bigmedicalInsuranceEnt) {
        this.bigmedicalInsuranceEnt = bigmedicalInsuranceEnt;
    }

    public BigDecimal getOutOfWorkSelf() {
        return outOfWorkSelf;
    }

    public void setOutOfWorkSelf(BigDecimal outOfWorkSelf) {
        this.outOfWorkSelf = outOfWorkSelf;
    }

    public BigDecimal getOutOfWorkEnt() {
        return outOfWorkEnt;
    }

    public void setOutOfWorkEnt(BigDecimal outOfWorkEnt) {
        this.outOfWorkEnt = outOfWorkEnt;
    }

    public BigDecimal getSelfTotal() {
        return selfTotal;
    }

    public void setSelfTotal(BigDecimal selfTotal) {
        this.selfTotal = selfTotal;
    }

    public BigDecimal getEntTotal() {
        return entTotal;
    }

    public void setEntTotal(BigDecimal entTotal) {
        this.entTotal = entTotal;
    }

    public BigDecimal getSocialTotal() {
        return socialTotal;
    }

    public void setSocialTotal(BigDecimal socialTotal) {
        this.socialTotal = socialTotal;
    }
}
