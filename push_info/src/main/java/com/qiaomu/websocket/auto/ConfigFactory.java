package com.qiaomu.websocket.auto;


import com.qiaomu.websocket.bootstrap.data.InChatToDataBaseService;
import com.qiaomu.websocket.bootstrap.verify.InChatVerifyService;
import com.qiaomu.websocket.common.bean.InitNetty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 默认配置工厂
 * Created by MySelf on 2018/12/21.
 */
public class ConfigFactory {

    /** Redis的ip地址，这你不会不知道吧？ */
   // @Value("${spring.redis.host}")
    //public static String RedisIP;

    /** 用户校验伪接口 */
    /*public static InChatVerifyService inChatVerifyService;

    *//** 用户获取数据伪接口 *//*
    public static InChatToDataBaseService inChatToDataBaseService;

    *//** 系统信息枚举服务接口 *//*
    public static FromServerService fromServerService;*/


}
