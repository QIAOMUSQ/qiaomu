package com.qiaomu.modules.welfare.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by wenglei on 2019/7/4.
 */
@TableName("welfare_points_user")
public class PointEntity {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String userId;
    private Integer points;
    private String createdAt;
    private String updatedAt;
}
