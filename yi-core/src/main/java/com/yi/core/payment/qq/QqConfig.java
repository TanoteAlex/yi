package com.yi.core.payment.qq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 微信相关配置信息
 * 
 * @author xuyh
 *
 */
@Component
@ConfigurationProperties(prefix = "qq")
@PropertySource(value = "classpath:/config/qq-config.yml", encoding = "UTF-8")
public class QqConfig {

	/** 运行环境 */
	public static final Boolean PROD_ENV = Boolean.TRUE;

	@Value("${app_id}")
	private String appId;

	@Value("${app_key}")
	private String appkey;

	@Value("${open_id_url}")
	private String openIdUrl;

	@Value("${union_id_url}")
	private String unionIdUrl;

	@Value("${user_info_url}")
	private String userInfoUrl;

	@Value("${success_code}")
	private String successCode;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getOpenIdUrl() {
		return openIdUrl;
	}

	public void setOpenIdUrl(String openIdUrl) {
		this.openIdUrl = openIdUrl;
	}

	public String getUnionIdUrl() {
		return unionIdUrl;
	}

	public void setUnionIdUrl(String unionIdUrl) {
		this.unionIdUrl = unionIdUrl;
	}

	public String getUserInfoUrl() {
		return userInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

}
