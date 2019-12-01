package com.qiaomu.modules.workflow.VO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:推送实体类
 * @Date 2019-07-14 22:11
 */
public class PushMessageVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String receivePhone;   //接受号码
    private String infoType;//消息类型
    private String time;    //推送时间
    private Long communityId;   //被推送人社区id
    private String message; //推送信息
    private Date createTime;
    private boolean status;
    private String transmissionContent;

    /**
     *
     * @param receivePhone 接收人号码
     * @param infoType 信息类型
     * @param communityId 推送人员社区的id
     * @param message 推送的信息
     * @param transmissionContent 透传信息内容
     */
    public PushMessageVO(String receivePhone, String infoType, Long communityId, String message, String transmissionContent) {
        this.receivePhone = receivePhone;
        this.infoType = infoType;
        this.communityId = communityId;
        this.message = message;
        this.createTime = new Date();
        this.status = false;
        this.transmissionContent = transmissionContent;
    }

    /**
     *
     * @param infoType 信息类型
     * @param communityId 推送人员社区的id
     * @param message 推送的信息
     * @param transmissionContent 透传信息内容
     */
    public PushMessageVO(String infoType, Long communityId, String message, String transmissionContent) {
        this.infoType = infoType;
        this.communityId = communityId;
        this.message = message;
        this.createTime = new Date();
        this.status = false;
        this.transmissionContent = transmissionContent;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTransmissionContent() {
        return transmissionContent;
    }

    public void setTransmissionContent(String transmissionContent) {
        this.transmissionContent = transmissionContent;
    }
}
