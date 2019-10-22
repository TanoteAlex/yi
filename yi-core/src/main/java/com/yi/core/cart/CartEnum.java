package com.yi.core.cart;

/**
 * 商品相关枚举
 * 
 * @author xuyh
 *
 */
public enum CartEnum {

	/** 有效 */
	STATE_VALID("有效", 0),
	/** 失效 */
	STATE_INVALID("失效", 1),

	/** 优惠券 */
	COUPON_COUPON_TYPE("优惠券", 1),

	/** 满多少钱可以 */
	COUPON_USE_CONDITION_TYPE_MONEY("满多少钱可以", 1),
	/** 满多少件可以 */
	COUPON_USE_CONDITION_TYPE_PIECE("满多少件可以", 2),

	COUPONRECEIVES_STATE_YES("可用", 1),

	COUPONRECEIVES_STATE_NO("不可用", 4),;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private CartEnum(String value, Integer code) {
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
