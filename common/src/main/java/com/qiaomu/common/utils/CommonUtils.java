package com.qiaomu.common.utils;

import java.util.Map;

/**
 * Created by wenglei on 2018/11/14.
 */
public class CommonUtils {
    public static <T> T getMapValue(String key,Map<String,T> params){
        if(null!=params){
            T value = params.get(key);
            return value;
        }
        return null;

    }


}
