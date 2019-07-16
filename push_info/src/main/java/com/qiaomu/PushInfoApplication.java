package com.qiaomu;


import com.qiaomu.common.reposity.PushMessageRepository;
import com.qiaomu.websocket.auto.InitServer;
import com.qiaomu.websocket.bootstrap.BootstrapServer;
import com.qiaomu.websocket.common.bean.InitNetty;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PushInfoApplication implements InitializingBean {

    @Autowired
    private InitNetty initNetty;

    @Autowired
    private BootstrapServer bootstrapServer;

    @Autowired
    private PushMessageRepository pushMessageRepository;



    @Autowired
    private InitServer initServer;

    public static void main(String[] args) {
        SpringApplication.run(PushInfoApplication.class,args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initServer.open();
        //redisGetMessage.startRedisMessage();
    }
}
