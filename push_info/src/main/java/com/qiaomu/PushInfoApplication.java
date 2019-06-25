package com.qiaomu;

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
    }
}
