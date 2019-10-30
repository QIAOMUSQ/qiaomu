package com.qiaomu.modules.propertycompany.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @author 李品先
 * @description: 广告浏览人数
 * @Date 2019-10-15 15:37
 */
@TableName("yw_advertise_browse")
public class AdvertiseBrowse {

    private Long advertiseId;

    private Date date;

    private Integer browsePerson;

    @TableField(exist = false)
    private String advertiseName;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

    public AdvertiseBrowse() {
    }

    public AdvertiseBrowse(Long advertiseId, Date date, Integer browsePerson) {
        this.advertiseId = advertiseId;
        this.date = date;
        this.browsePerson = browsePerson;
    }

    public Long getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(Long advertiseId) {
        this.advertiseId = advertiseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBrowsePerson() {
        return browsePerson;
    }

    public void setBrowsePerson(Integer browsePerson) {
        this.browsePerson = browsePerson;
    }

    public String getAdvertiseName() {
        return advertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        this.advertiseName = advertiseName;
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
}
