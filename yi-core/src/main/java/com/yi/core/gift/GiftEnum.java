package com.yi.core.gift;

public enum GiftEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 未支付 */
	STATE_UNPAID("未支付", 0),
	/** 有效 */
	STATE_VALID("有效", 1),
	/** 失效 */
	STATE_INVALID("失效", 2),

	/** 神秘礼物-否 */
	MYSTERY_GIFT_NO("否", 0),
	/** 神秘礼物-是 */
	MYSTERY_GIFT_YES("是", 1),

	/** 领取状态-待领取 */
	RECEIVE_STATE_WAIT("待领取", 0),
	/** 领取状态-已领取 */
	RECEIVE_STATE_ALREADY("已领取", 1),
	/** 领取状态-已超时 */
	RECEIVE_STATE_EXPIRE("已超时", 2),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private GiftEnum(String value, Integer code) {
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
