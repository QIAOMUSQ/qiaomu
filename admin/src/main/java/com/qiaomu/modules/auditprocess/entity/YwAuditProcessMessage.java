package com.qiaomu.modules.auditprocess.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:01
 */
public class YwAuditProcessMessage implements Serializable {
    public static final Long serialVersionUID = Long.valueOf(1L);

    @TableId
    private Long id;
    private Long communityId;
    private String phoneOne;
    private String phoneTwo;
    private String reportPerson;//上报人
    private String superintendentPhone;//监管人号码
    private String processName;//流程名称
    private String processType;//流程类型
    private Long companyId;

    @TableField(exist = false)
    private String communityName;

    @TableField(exist = false)
    private String phoneOneName;

    @TableField(exist = false)
    private String phoneTwoName;

    @TableField(exist = false)
    private String reportPersonName;
    private Date createTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPhoneOne() {
        return this.phoneOne;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public String getPhoneTwo() {
        return this.phoneTwo;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public String getReportPerson() {
        return this.reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getSuperintendentPhone() {
        return this.superintendentPhone;
    }

    public void setSuperintendentPhone(String superintendentPhone) {
        this.superintendentPhone = superintendentPhone;
    }

    public String getProcessName() {
        return this.processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessType() {
        return this.processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCommunityName() {
        return this.communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhoneOneName() {
        return this.phoneOneName;
    }

    public void setPhoneOneName(String phoneOneName) {
        this.phoneOneName = phoneOneName;
    }

    public String getPhoneTwoName() {
        return this.phoneTwoName;
    }

    public void setPhoneTwoName(String phoneTwoName) {
        this.phoneTwoName = phoneTwoName;
    }

    public String getReportPersonName() {
        return this.reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }
}
