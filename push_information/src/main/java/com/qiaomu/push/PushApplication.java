package com.qiaomu.push;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 李品先
 * @description:推送
 * @Date 2019-11-07 10:43
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.qiaomu.**.dao"})
@ComponentScan({"com.qiaomu.*"})
@ServletComponentScan
public class PushApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushApplication.class, args);
    }
}
