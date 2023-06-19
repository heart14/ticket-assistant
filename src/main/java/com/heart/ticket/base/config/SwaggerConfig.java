package com.heart.ticket.base.config;


import com.heart.ticket.base.common.SystemProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * About:
 * Other:
 * Created: Administrator on 2022/3/10 12:25.
 * Editored:
 */
@Configuration
public class SwaggerConfig {

    // swagger各版本整理：https://blog.csdn.net/qq_33334411/article/details/125655201
    // swagger文档地址 http://{baseurl}:{port}/{context-path}/swagger-ui/index.html

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.heart.ticket.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api Documentation - " + SystemProperties.APPLICAITON_NAME)
                .description("Api Description")
                .contact(new Contact("heart", "heart@qq.com", "heart@qq.com"))
                .version("v1.0")
                .build();
    }
}
