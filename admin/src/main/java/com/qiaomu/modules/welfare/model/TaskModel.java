package com.qiaomu.modules.welfare.model;

/**
 * Created by wenglei on 2019/6/20.
 */
public class TaskModel {
    private String serviceId;
    private String serviceName;
    private String seviceDetail;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSeviceDetail() {
        return seviceDetail;
    }

    public void setSeviceDetail(String seviceDetail) {
        this.seviceDetail = seviceDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    private String status;// 已领取、待领取
    private String points;
    private String publishUserId;//发布任务的人
    private String receiveUserId;//领取任务的人
}
