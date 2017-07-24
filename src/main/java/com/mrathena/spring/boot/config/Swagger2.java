package com.mrathena.spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/* 
 * http://IP:port/{context-path}/swagger-ui.html 
 * eg:http://localhost/swagger-ui.html 
 */ 

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createApi() {
		ApiInfo info = new ApiInfoBuilder()
            .title("This is the Title")
            .description("This is the Description")
            .termsOfServiceUrl("http://spring.boot.mrathena.top/")
            .version("1.0.0")
            .build();
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(info)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.mrathena.spring.boot.start.controller"))
			.paths(PathSelectors.any())
			.build();
	}
	
}