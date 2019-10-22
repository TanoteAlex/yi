package com.yi.core.stats;

/**
 * 订单相关枚举
 * 
 * @author xuyh
 *
 */
public enum StatsEnum {

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private StatsEnum(String value, Integer code) {
		this.value = value;
		this.code = code;
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
