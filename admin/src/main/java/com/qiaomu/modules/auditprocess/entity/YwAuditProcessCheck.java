package com.qiaomu.modules.auditprocess.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-21 16:00
 */
@TableName("yw_audit_process_check")
public class YwAuditProcessCheck
        implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String type;
    private String clientPhone;
    private Long communityId;
    private Long companyId;
    private String detailPhoneOne;
    private String detailOpinionOne;
    private Date detailOneDate;
    private String detailPhoneTwo;
    private String detailOpinionTwo;
    private Date detailTwoDate;
    private String detailPhoneReport;
    private String detailOpinionReport;
    private String userOpinion;
    private Long processId;
    private String detail;
    private Date createTime;

    @TableField(exist = false)
    private String processName;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientPhone() {
        return this.clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Long getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDetailPhoneOne() {
        return this.detailPhoneOne;
    }

    public void setDetailPhoneOne(String detailPhoneOne) {
        this.detailPhoneOne = detailPhoneOne;
    }

    public String getDetailOpinionOne() {
        return this.detailOpinionOne;
    }

    public void setDetailOpinionOne(String detailOpinionOne) {
        this.detailOpinionOne = detailOpinionOne;
    }

    public Date getDetailOneDate() {
        return this.detailOneDate;
    }

    public void setDetailOneDate(Date detailOneDate) {
        this.detailOneDate = detailOneDate;
    }

    public String getDetailPhoneTwo() {
        return this.detailPhoneTwo;
    }

    public void setDetailPhoneTwo(String detailPhoneTwo) {
        this.detailPhoneTwo = detailPhoneTwo;
    }

    public String getDetailOpinionTwo() {
        return this.detailOpinionTwo;
    }

    public void setDetailOpinionTwo(String detailOpinionTwo) {
        this.detailOpinionTwo = detailOpinionTwo;
    }

    public Date getDetailTwoDate() {
        return this.detailTwoDate;
    }

    public void setDetailTwoDate(Date detailTwoDate) {
        this.detailTwoDate = detailTwoDate;
    }

    public String getDetailPhoneReport() {
        return this.detailPhoneReport;
    }

    public void setDetailPhoneReport(String detailPhoneReport) {
        this.detailPhoneReport = detailPhoneReport;
    }

    public String getDetailOpinionReport() {
        return this.detailOpinionReport;
    }

    public void setDetailOpinionReport(String detailOpinionReport) {
        this.detailOpinionReport = detailOpinionReport;
    }

    public String getUserOpinion() {
        return this.userOpinion;
    }

    public void setUserOpinion(String userOpinion) {
        this.userOpinion = userOpinion;
    }

    public Long getProcessId() {
        return this.processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProcessName() {
        return this.processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}