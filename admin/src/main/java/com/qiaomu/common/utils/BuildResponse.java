package com.qiaomu.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wenglei on 2019/5/25.
 */
public class BuildResponse {
    public static Map<String,Object> success(){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode","1000");
        returnMap.put("respMsg","成功");
        return returnMap;
    }
    public static Map<String,Object> success(Object data){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode","1000");
        returnMap.put("respMsg","成功");
        returnMap.put("data",data);
        return returnMap;
    }

    public static Map<String,Object> fail(){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode","1001");
        returnMap.put("respMsg","失败");
        return returnMap;
    }

    public static Map<String,Object> fail(String respCode,String msg){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode",respCode);
        returnMap.put("respMsg",msg);
        return returnMap;
    }
}
