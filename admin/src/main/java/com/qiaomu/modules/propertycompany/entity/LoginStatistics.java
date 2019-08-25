package com.qiaomu.modules.propertycompany.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 李品先
 * @description: 社区登录统计
 * @Date 2019-08-24 16:20
 */
@TableName("yw_community_login_statistics")
public class LoginStatistics implements Serializable {
    public static final long serialVersionUID = 1L;

    private String date;

    private String type;//1:日 2：月 3：年

    private Long communityId;

    private int number;

    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;
    @TableField(exist = false)
    private String cityCode;
    @TableField(exist = false)
    private Long cityId;
    @TableField(exist = false)
    private String name;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
