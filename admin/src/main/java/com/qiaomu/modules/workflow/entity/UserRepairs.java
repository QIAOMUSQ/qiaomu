package com.qiaomu.modules.workflow.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 李品先
 * @description:工作人员和维修信息关联表
 * @Date 2019-10-25 22:59
 */
@TableName("yw_user_repairs")
public class UserRepairs implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long userId;

    private Long repairsId;

    @TableField(exist = false)
    private String repairsName;

    @TableField(exist = false)
    private String repairsPhone;

    public UserRepairs() {
    }

    public UserRepairs(Long userId) {
        this.userId = userId;
    }

    public UserRepairs(Long userId, Long repairsId) {
        this.userId = userId;
        this.repairsId = repairsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRepairsId() {
        return repairsId;
    }

    public void setRepairsId(Long repairsId) {
        this.repairsId = repairsId;
    }

    public String getRepairsPhone() {
        return repairsPhone;
    }

    public void setRepairsPhone(String repairsPhone) {
        this.repairsPhone = repairsPhone;
    }

    public String getRepairsName() {
        return repairsName;
    }

    public void setRepairsName(String repairsName) {
        this.repairsName = repairsName;
    }
}
