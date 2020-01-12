package com.qiaomu.modules.workflow.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description；公告表
 * @Date 2019-04-22 21:47
 */
@TableName("yw_invitation")
public class InvitationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField
    private Long id;
    private Long communityId;
    private Date createTime;
    private Date updateTime;
    @Length(max=30, message="用户名长度必须0-30之间")
    private String title;
    private String content;
    private String imgJson;
    @TableField(exist = false)
    private String communityName;
    @TableField(exist = false)
    private Long companyId;
    @TableField(exist = false)
    private Long userId;
    @TableField(exist = false)
    private String contentHtml;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgJson() {
        return imgJson;
    }

    public void setImgJson(String imgJson) {
        this.imgJson = imgJson;
    }


    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
