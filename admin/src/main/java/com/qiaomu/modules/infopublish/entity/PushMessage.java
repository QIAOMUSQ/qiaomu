package com.qiaomu.modules.infopublish.entity;

import java.io.Serializable;

/**
 * @author 李品先
 * @description:推送实体类
 * @Date 2019-07-14 22:11
 */
public class PushMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phone;   //被推送人号码
    /**
     * 推送类型
     *  0:推送到个人
     *  1：推送到群组
     *  2：推送到社区
     *  4：推送到全部用户
     */
    private String type;    //推送类型
    private String infoType;//消息类型
    private String time;    //推送时间
    private Long communityId;   //被推送人社区id
    private String message; //推送信息
    private String userPhone;//推送人号码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public PushMessage(String phone, String type, String infoType, String time, String message, String userPhone, Long communityId) {
        this.phone = phone;
        this.type = type;
        this.infoType = infoType;
        this.time = time;
        this.message = message;
        this.userPhone = userPhone;
        this.communityId = communityId;
    }

    public PushMessage() {
    }
}
