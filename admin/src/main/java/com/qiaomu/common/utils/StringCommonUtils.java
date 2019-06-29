package com.qiaomu.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author 李品先
 * @description:
 * @Date 2019-06-30 0:53
 */
public class StringCommonUtils {

    public static String returnNullData(String data){
        if(StringUtils.isNotBlank(data)){
            return data;
        }else {
            return "";
        }
    }
}
