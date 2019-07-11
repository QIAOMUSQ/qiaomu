package com.qiaomu;


import com.qiaomu.websocket.auto.ConfigFactory;
import com.qiaomu.websocket.auto.InitServer;
import com.qiaomu.websocket.common.bean.InitNetty;
import com.qiaomu.websocket.users.DataBaseServiceImpl;
import com.qiaomu.websocket.users.FromServerServiceImpl;
import com.qiaomu.websocket.users.VerifyServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 李品先
 * @description:消息推送
 * @Date 2019-06-24 11:02
 */
@SpringBootApplication
@ComponentScan({"com.qiaomu"})
public class PushInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PushInfoApplication.class,args);
       // new NettyServer().run();

        //连接redis服务
      //  ConfigFactory.RedisIP = "127.0.0.1";
        ConfigFactory.initNetty = new InitNetty();
        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
        ConfigFactory.inChatToDataBaseService = new DataBaseServiceImpl();
        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;
        InitServer.open();
    }
}
