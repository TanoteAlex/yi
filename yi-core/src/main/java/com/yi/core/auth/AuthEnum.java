package com.yi.core.auth;

/**
 * 权限相关枚举
 * 
 * @author xuyh
 *
 */
public enum AuthEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private AuthEnum(String value, Integer code) {
		this.code = code;
		this.value = value;
	}

	// get set 方法
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
