package com.qiaomu.modules.propertycompany.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:广告实体
 * @Date 2019-08-09 23:37
 */
@TableName("yw_advertise")
public class Advertise implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String title;//标题

    private String brief;//简介

    private String content;

    private String imgs;

    private Date createTime;

    private String startTime;//生效开始时间

    private String endTime;//生效结束时间

    private Long merchantId;//商户id

    private String httpsUrl;//外链httls跳转地址

    private String https;//是否支持外链

    private String communityIds;//社区Id

    private String showLocation;//显示位置 1：主页头，2：公告位置 3：霸屏3秒

    private String type;//广告类型 1:全部图片 2：部分图片 3：全部文字

    @TableField(exist = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getHttpsUrl() {
        return httpsUrl;
    }

    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

    public String getHttps() {
        return https;
    }

    public void setHttps(String https) {
        this.https = https;
    }

    public String getCommunityIds() {
        return communityIds;
    }

    public void setCommunityIds(String communityIds) {
        this.communityIds = communityIds;
    }

    public String getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
