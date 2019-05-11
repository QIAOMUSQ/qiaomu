package com.qiaomu.modules.android.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;

/**
 * @author 李品先
 * @description:社区实体
 * @Date 2018-11-28 21:30
 */
@TableName("yw_city")
public class City {

    @TableId
    private Long id;
    private String code;
    private String name;

    @TableField(exist = false)
    private Long parentId;

    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<?> list;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return this.list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
