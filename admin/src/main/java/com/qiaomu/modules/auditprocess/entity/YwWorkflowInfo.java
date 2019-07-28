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
@TableName("yw_workflow_info")
public class YwWorkflowInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String type;//流程状态 0：申请 1：一级接收 11：一级受理完成 2：二级接收 21：二级受理完成  3：上报  4通过  5不通过
    private Long userId;
    private Long communityId;   //社区id
    private Long companyId;     //  物业id

    private String detailOpinionOne;    //  第一处理人意见
    private Date detailOneDate;     //处理时间

    private String detailOpinionTwo;
    private Date detailTwoDate;

    private String detailOpinionReport; //上报人意见
    private Date reportDate;
    private String userOpinion; //用户评价
    private Long workflowId; //流程类型id
    private String detail;  //事务描述细节
    private Date createTime;    //发起时间
    private String location;    //事发位置
    private String pictureId;
    private String serviceDate;//上门维修时间
    private String workflowType;//流程类型

    private String finalityOpinion;//评审终结意见

    private Date finalityDate;

    @TableField(exist = false)
    private String detailPhoneOne;  //  第一处理人号码
    @TableField(exist = false)
    private String detailPhoneTwo;
    @TableField(exist = false)
    private String detailPhoneReport;   //上报人号码


    @TableField(exist = false)
    private String superintendentName;//评审人名称
    @TableField(exist = false)
    private String superintendentPhone;//评审号码

    @TableField(exist = false)
    private String processName;
    @TableField(exist = false)
    private String detailPhoneOneName;
    @TableField(exist = false)
    private String detailPhoneTwoName;
    @TableField(exist = false)
    private String detailPhoneReportName;
    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String userName;

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



    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCompanyId() {
        return companyId;
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

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public String getDetailPhoneOneName() {
        return detailPhoneOneName;
    }

    public void setDetailPhoneOneName(String detailPhoneOneName) {
        this.detailPhoneOneName = detailPhoneOneName;
    }

    public String getDetailPhoneTwoName() {
        return detailPhoneTwoName;
    }

    public void setDetailPhoneTwoName(String detailPhoneTwoName) {
        this.detailPhoneTwoName = detailPhoneTwoName;
    }

    public String getDetailPhoneReportName() {
        return detailPhoneReportName;
    }

    public void setDetailPhoneReportName(String detailPhoneReportName) {
        this.detailPhoneReportName = detailPhoneReportName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getFinalityOpinion() {
        return finalityOpinion;
    }

    public void setFinalityOpinion(String finalityOpinion) {
        this.finalityOpinion = finalityOpinion;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSuperintendentName() {
        return superintendentName;
    }

    public void setSuperintendentName(String superintendentName) {
        this.superintendentName = superintendentName;
    }

    public String getSuperintendentPhone() {
        return superintendentPhone;
    }

    public void setSuperintendentPhone(String superintendentPhone) {
        this.superintendentPhone = superintendentPhone;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getFinalityDate() {
        return finalityDate;
    }

    public void setFinalityDate(Date finalityDate) {
        this.finalityDate = finalityDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}