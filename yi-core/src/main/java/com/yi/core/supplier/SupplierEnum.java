package com.yi.core.supplier;

/**
 * 供应商 相关枚举
 * 
 * @author xuyh
 *
 */
public enum SupplierEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 提现申请状态 -待发放 */
	APPLY_STATE_WAIT_GRANT("待发放", 1),
	/** 提现申请状态 -已发放 */
	APPLY_STATE_ALREADY_GRANT("已发放", 2),
	/** 提现申请状态 -发放异常 */
	APPLY_STATE_GRANT_FAIL("发放异常", 3),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private SupplierEnum(String value, Integer code) {
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
