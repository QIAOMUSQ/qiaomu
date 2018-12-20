package com.renren.app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 李品先
 * @description:
 * @Date 2018-12-21 0:34
 */
@TableName("test")
public class TestTable implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @TableId
    private Long id;

    private String name;

    private String code;
}
