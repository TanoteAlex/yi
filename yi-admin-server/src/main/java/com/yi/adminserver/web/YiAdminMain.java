package com.yi.adminserver.web;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 启动配置项
 * 
 * @author yihz
 *
 */
@ComponentScan(basePackages = { "com.yi", "com.yihz" })
@SpringBootApplication
public class YiAdminMain {
	public static void main(String[] args) {

		SpringApplication.run(YiAdminMain.class, args);

	}

	/**
	 * 跨域过滤器
	 *
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		// CORS 请求缓存4个小时
		corsConfiguration.setMaxAge(TimeUnit.HOURS.toSeconds(4));

		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);

		return corsConfiguration;
	}
}
