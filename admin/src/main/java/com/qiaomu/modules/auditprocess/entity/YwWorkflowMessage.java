package com.qiaomu.modules.auditprocess.entity;

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
    private Integer communityId;   //社区id
    private String phoneOne;    //第一处理人
    private String phoneTwo;    //  第二处理人
    private String reportPerson;    //上报人
    private String superintendentPhone; //监管人
    private String processName; //流程名称
    private String dicValue; //字典值
    private Integer companyId; //物业ID



    @TableField(exist = false)
    private String communityName;

    private String phoneOneName;
    private String phoneTwoName;
    private String reportPersonName;
    private String superintendentName;
    private Date createTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
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


    public String getSuperintendentName() {
        return superintendentName;
    }

    public void setSuperintendentName(String superintendentName) {
        this.superintendentName = superintendentName;
    }
}
