package com.qiaomu.modules.workflow.entity;

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
    /**
     *  0：申请 1：处理人员处理 2: 处理完成
     */
    private String type;//流程状态
    private Long userId;
    private Long communityId;   //社区id
    private Long companyId;     //  物业id

    private String detailOpinionOne;    // 工作人员
    private Date detailOneDate;     //处理时间
    private String userOpinion; //用户评价
    private Long workflowId; //流程类型id
    private String detail;  //事务描述细节
    private Date createTime;    //发起时间
    private String location;    //事发位置
    private String pictureId;
    private String serviceDate;//上门维修时间
    private String workflowType;//流程类型
    private Date finalityDate;
    private String status;
    private String starType;//星级评级

    @TableField(exist = false)
    private String detailPhoneOne;  //  第一处理人号码
    @TableField(exist = false)
    private String processName;
    @TableField(exist = false)
    private String detailPhoneOneName;
    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String workflowIds; //流程类型id

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


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(String workflowIds) {
        this.workflowIds = workflowIds;
    }

    public String getDetailPhoneOneName() {
        return detailPhoneOneName;
    }

    public void setDetailPhoneOneName(String detailPhoneOneName) {
        this.detailPhoneOneName = detailPhoneOneName;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }
}