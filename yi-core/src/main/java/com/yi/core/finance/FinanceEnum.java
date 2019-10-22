package com.yi.core.finance;

/**
 * 财务相关枚举
 * 
 * @author xuyh
 *
 */
public enum FinanceEnum {

	/** 1线下转账 */
	PAYMENT_METHOD_OFFLINE("线下转账", 1),
	/** 2线上转账 */
	PAYMENT_METHOD_ONLINE("线上转账", 2),

	/** 1待发放 */
	APPLY_STATE_WAIT_GRANT("待发放", 1),
	/** 2已发放 */
	APPLY_STATE_ALREADY_GRANT("已发放", 2),
	/** 3发放异常 */
	APPLY_STATE_EXCEPTION("发放异常", 3),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private FinanceEnum(String value, Integer code) {
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
