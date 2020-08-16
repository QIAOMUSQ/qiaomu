/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.qiaomu.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

/**
 * Swagger配置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2018-01-16
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
//@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig{

    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey(AUTHORIZATION, AUTHORIZATION, "header");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理")
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.qiaomu"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理")
                .description("admin文档")
                .termsOfServiceUrl("")
                .version("3.2.0")
                .build();
    }

}