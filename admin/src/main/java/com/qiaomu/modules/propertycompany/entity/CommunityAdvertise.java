package com.qiaomu.modules.propertycompany.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 李品先
 * @description:
 * @Date 2019-08-10 15:53
 */
@TableName("yw_community_advertise")
public class CommunityAdvertise implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long communityId;

    private Long advertiseId;

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

    public Long getAdvertiseId() {
        return advertiseId;
    }

    public void setAdvertiseId(Long advertiseId) {
        this.advertiseId = advertiseId;
    }
}
