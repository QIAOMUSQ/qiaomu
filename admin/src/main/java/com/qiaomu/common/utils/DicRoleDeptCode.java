package com.qiaomu.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李品先
 * @description:物业角色-角色-部门对应编码
 * @Date 2019-04-08 21:25
 */
public interface DicRoleDeptCode {
    public static final Map<String,String> role_dept_map = new HashMap<String,String>(){

        {   put("0", "1_5");
            put("1", "2_6");
            put("2", "3_7");
            put("3", "4_8");
            put("4", "5_9");
        }
    };
}
