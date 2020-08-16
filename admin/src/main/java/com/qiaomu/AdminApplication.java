package com.qiaomu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.qiaomu.modules.*.dao"})
@EnableScheduling
@EnableSwagger2
public class AdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		log.info("\n----------------------------------------------------------\n\t" +
				"Application Jeecg-Boot is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"External: \thttp://" + ip + ":" + port + path + "/\n\t" +
				"swagger-ui: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminApplication.class);
	}*/
}
