package com.qiaomu.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李品先
 * @description:物业角色-角色-部门对应编码
 * @Date 2019-04-08 21:25
 */
public interface DicRoleCode {
    public static final Map<String,String> role_dept_map = new HashMap<String,String>(){

        {
            put("1", "0");//超級管理員
            put("2", "1");//物业管理员
            put("3", "2");//社区管理员
            put("4", "3");//物业业主
            put("5", "4");//游客
        }
    };
}
