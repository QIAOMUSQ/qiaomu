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
    private String type;//流程状态 0：申请 1：一级接收 11：一级受理完成 2：二级接收 21：二级受理完成  3：上报  4通过  5不通过
    private String clientPhone;
    private Long communityId;//社区ID
    private Long companyId;//物业ID
    private String detailPhoneOne;//一级处理人电话
    private String detailOpinionOne;//以及处理人意见
    private Date detailOneDate;
    private String detailPhoneTwo;
    private String detailOpinionTwo;
    private Date detailTwoDate;
    private String detailPhoneReport;//上报人电话
    private String detailOpinionReport;//上报人处理意见
    private String userOpinion;//用户意见
    private Long processId; //进程ID
    private String detail;//详情
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