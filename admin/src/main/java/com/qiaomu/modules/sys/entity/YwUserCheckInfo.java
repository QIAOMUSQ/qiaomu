package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 李品先
 * @description:用户审核信息表
 * @Date 2019-03-31 21:50
 */
@TableName("yw_user_check_info")
public class YwUserCheckInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String userPhone;

    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
