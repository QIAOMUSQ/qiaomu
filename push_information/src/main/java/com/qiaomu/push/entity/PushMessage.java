package com.qiaomu.push.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:推送实体类
 * @Date 2019-07-14 22:11
 */
@TableName("push_message_data")
public class PushMessage implements Cloneable,Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    private String receivePhone;   //接受号码
    private String infoType;//消息类型
    private String time;    //推送时间
    private Long communityId;   //被推送人社区id
    private String message; //推送信息
    private Date createTime;
    private boolean status;
    private String transmissionContent;
    @TableField(exist = false)
    private String clientId;
    public PushMessage() {
    }

    public PushMessage(String receivePhone, boolean status) {
        this.receivePhone = receivePhone;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }


    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTransmissionContent() {
        return transmissionContent;
    }

    public void setTransmissionContent(String transmissionContent) {
        this.transmissionContent = transmissionContent;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public PushMessage clone() {
        PushMessage person = null;
        try {
            person = (PushMessage)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return person;
    }

}
