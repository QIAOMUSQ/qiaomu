package com.qiaomu.modules.welfare.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by wenglei on 2019/6/20.
 */
@TableName("welfare_task_recevie_user")
public class TaskRecevieUserEntity {
    private String id;
    private String receiveUserId;//领取任务的人
    private String serviceId;//任务ID
    private String status;//已取消、执行中、审核中、已完成
    private String createdAt;
    private String updatedAt;
    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
