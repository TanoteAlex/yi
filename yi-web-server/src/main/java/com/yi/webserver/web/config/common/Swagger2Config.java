package com.yi.webserver.web.config.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 * 
 * @author xuyh
 *
 */
//@Configuration
//@EnableSwagger2
public class Swagger2Config {

	@Value("${spring.profiles.active}")
	private String site;

	@Value("${yi.host}")
	private String host;

	@Value("${yi.web-port}")
	private String port;

	/**
	 * 生产环境
	 * 
	 * @return Boolean
	 */
	public boolean runProd() {
		if ("prod".equals(site)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 测试环境
	 * 
	 * @return Boolean
	 */
	public boolean runTest() {
		if ("test".equals(site)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 开发环境
	 * 
	 * @return Boolean
	 */
	public boolean runDev() {
		if ("dev".equals(site)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取站点
	 * 
	 * @return String
	 */
	public String getDomain() {
		if (this.runDev()) {
			return "http://" + host + ":" + port;
		} else {
			return "http://" + host;
		}
	}

	@Bean
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(this.apiInfo());
		if (this.runDev()) {
			docket.host(host + ":" + port);
		} else {
			docket.host(host);
		}
		if (this.runProd()) {
			docket.select().apis(RequestHandlerSelectors.basePackage("com.yi.webserver.web")).paths(PathSelectors.none()).build();
		} else {
			docket.select().apis(RequestHandlerSelectors.basePackage("com.yi.webserver.web")).paths(PathSelectors.any()).build();
		}
		return docket;
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title("壹壹项目RESTful API");
		apiInfoBuilder.description("壹壹项目接口文档");
		apiInfoBuilder.termsOfServiceUrl(this.getDomain());
		apiInfoBuilder.version("1.0");
		return apiInfoBuilder.build();
	}

}
