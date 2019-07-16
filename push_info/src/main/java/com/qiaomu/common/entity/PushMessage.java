package com.qiaomu.common.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author 李品先
 * @description:推送消息实体
 * @Date 2019-07-10 14:50
 */

@Entity
@Table(name = "push_message_data")
public class PushMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;   //收件人号码
    private Date createTime;    //创建时间
    private String time;    //推送时间
    /**
     * 推送类型
     *  0:推送到个人
     *  1：推送到群组
     *  2：推送到社区
     *  4：推送到全部用户
     */
    private String type;
    private String message; //推送信息
    private Long communityId;   //被推送人的社区id
    private boolean status; //状态，是否已经推送 true:已推送 false:未推送
    private String userPhone;//推送人号码
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
