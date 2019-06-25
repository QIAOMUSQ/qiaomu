package com.qiaomu.modules.infopublish_publish.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:发帖信息
 * @Date 2019-04-22 21:47
 */
@TableName("info_invitation")
public class InvitationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String phone;
    private Integer communityId;
    private Integer companyId;
    private Date createTime;
    @Length(max=30, message="用户名长度必须0-30之间")
    private String title;
    private String content;
    private String imgId;
    private Long browseNum;//浏览量
    private String type;//0：全部可见 1：本社区可见
    private String info_type;//多种类型
    private String enComment;//0：可评论 1：不可评论

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public Long getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Long browseNum) {
        this.browseNum = browseNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }

    public String getEnComment() {
        return enComment;
    }

    public void setEnComment(String enComment) {
        this.enComment = enComment;
    }
}
