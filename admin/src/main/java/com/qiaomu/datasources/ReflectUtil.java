/**
 * Copyright  2013-6-17 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:58:33
 * 版本号： v1.0
 */

package com.qiaomu.datasources;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author benwq
 * @Description:
 * @Date: 9:17 2018/4/13
 */
public class ReflectUtil {

    /**
     * @author benwq
     * @Description: 寻找方法名唯一的方法
     * @Date: 9:17 2018/4/13
     */
    public static Method findUniqueMethod(Class<?> clazz, String name) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }
}
