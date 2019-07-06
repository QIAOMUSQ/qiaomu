package com.qiaomu.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 返回码标准：
 接口必须携带字段：respCode，respMsg
 成功：respCode=1000  respMsg=成功 如果还想携带一个对象，塞入data里面，如果只是携带几个参数，直接putkey
 eg1:
 BuildResponse.success();//直接返回
 eg2:
 Map<String,Object> returnMap = BuildResponse.success();
 returnMap.put("yourKey","yourValue");//几个key
 eg3:
 Object obj = new Object();
 BuildResponse.success(obj);//对象


 失败：respCode=1001  respMsg=失败 如果还想携带更多失败信息，增加errorMsg字段
 eg1:
 BuildResponse.fail();//直接错误返回

 eg2:
 BuildResponse.fail("不能重复");//定义errorMsg

 eg3:
 BuildResponse.fail("2001","不能重复");//自定义返回码，自定义码按照模块定义首位数字，一般情况不要使用
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

    public static Map<String,Object> fail(String errorMsg){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode","1001");
        returnMap.put("respMsg","失败");
        returnMap.put("errorMsg",errorMsg);
        return returnMap;
    }

    public static Map<String,Object> fail(String respCode,String errorMsg){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("respCode",respCode);
        returnMap.put("respMsg","失败");
        returnMap.put("errorMsg",errorMsg);
        return returnMap;
    }
}
