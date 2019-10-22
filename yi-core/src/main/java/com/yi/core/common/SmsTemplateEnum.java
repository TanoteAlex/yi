package com.yi.core.common;

/**
 * 短信模板枚举
 * 
 * @author xuyh
 *
 */
public enum SmsTemplateEnum {
	/** 注册-验证码${code}，您正在注册成为新用户，感谢您的支持！ */
	USER_REGISTER("SMS_143925153", "用户注册"),
	/** 登录确认-验证码${code}，您正在登录，若非本人操作，请勿泄露。 */
	LOGIN_CONFIRM("SMS_143925155", "登录确认"),
	/** 修改密码-验证码${code}，您正在尝试修改登录密码，请妥善保管账户信息。 */
	CHANGE_PASSWORD("SMS_143925152", "修改密码"),

	;
	// 成员变量
	private String code;
	private String description;

	// 构造方法
	private SmsTemplateEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	// get set 方法
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
