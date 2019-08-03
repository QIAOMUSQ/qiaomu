package com.qiaomu.modules.workflow.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:01
 */
@TableName("yw_workflow_message")
public class YwWorkflowMessage implements Serializable {
    public static final Long serialVersionUID = Long.valueOf(1L);

    @TableId
    private Long id;
    private Long communityId;   //社区id
    private String phoneOneId;    //第一处理人
    private String phoneTwoId;    //  第二处理人
    private String reportPersonId;    //上报人
    private String superintendentId; //监管人
    private String processName; //流程名称
    private String dicValue; //字典值
    private Long companyId; //物业ID
    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private String phoneOneName;
    @TableField(exist = false)
    private String phoneTwoName;
    @TableField(exist = false)
    private String reportPersonName;
    @TableField(exist = false)
    private String superintendentName;
    @TableField(exist = false)
    private String dicValueName; //字典值
    private Date createTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneOneId() {
        return phoneOneId;
    }

    public void setPhoneOneId(String phoneOneId) {
        this.phoneOneId = phoneOneId;
    }

    public String getPhoneTwoId() {
        return phoneTwoId;
    }

    public void setPhoneTwoId(String phoneTwoId) {
        this.phoneTwoId = phoneTwoId;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getSuperintendentId() {
        return superintendentId;
    }

    public void setSuperintendentId(String superintendentId) {
        this.superintendentId = superintendentId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
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


    public String getSuperintendentName() {
        return superintendentName;
    }

    public void setSuperintendentName(String superintendentName) {
        this.superintendentName = superintendentName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDicValueName() {
        return dicValueName;
    }

    public void setDicValueName(String dicValueName) {
        this.dicValueName = dicValueName;
    }
}
