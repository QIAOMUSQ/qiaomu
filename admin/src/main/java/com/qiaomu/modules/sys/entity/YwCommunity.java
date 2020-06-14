package com.qiaomu.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 李品先
 * @description:
 * @Date 2019-04-19 22:53
 */

@TableName("yw_community")
@Data
public class YwCommunity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String name;
    private Long cityId;
    private String describe;
    private String address;
    private Long adminId;
    private Long companyId;
    private Date createTime;
    private String enable;
    private Date deleteTime;
    private int households;//户数

    @TableField(exist = false)
    private String companyName;

    @TableField(exist = false)
    private String cityName;

    @TableField(exist = false)
    private String admin;
    private String cityCode;

}
