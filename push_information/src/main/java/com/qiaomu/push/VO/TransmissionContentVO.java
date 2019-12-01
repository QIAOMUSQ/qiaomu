package com.qiaomu.push.VO;

/**
 * @author 李品先
 * @description:消息透传内容
 * @Date 2019-11-07 16:07
 */
public class TransmissionContentVO {

    private String typeName;//功能名称

    private Long communityId;//社区id

    private Long messageId;//消息对应的信息id
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
