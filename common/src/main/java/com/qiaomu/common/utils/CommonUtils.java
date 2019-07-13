package com.qiaomu.common.utils;

import java.io.File;
import java.util.Map;
import java.util.UUID;

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

    //生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
    public static String mkFileName(String fileName){
        String postfix=fileName.split("\\.")[1];
        return UUID.randomUUID().toString()+"."+postfix;
    }
    public static String mkFilePath(String savePath,String fileName){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = fileName.hashCode();
        int dir1 = hashcode&0xf;
        int dir2 = (hashcode&0xf0)>>4;
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }



}
