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
    private String type;
    private String clientPhone;
    private Integer communityId;   //社区id
    private Integer companyId;     //  物业id
    private String detailPhoneOne;  //  第一处理人号码
    private String detailOpinionOne;    //  第一处理人意见
    private Date detailOneDate;     //处理时间
    private String detailPhoneTwo;
    private String detailOpinionTwo;
    private Date detailTwoDate;
    private String detailPhoneReport;   //上报人号码
    private String detailOpinionReport; //上报人意见
    private String userOpinion; //用户评价
    private Integer workflowId; //流程类型id
    private String detail;  //事务描述细节
    private Date createTime;    //发起时间
    private String location;    //事发位置
    private String pictureId;
    private Date serviceDate;//上门维修时间

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

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
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

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }
}