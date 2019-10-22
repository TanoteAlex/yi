package com.yi.core.promotion;

/**
 * 团购活动相关枚举
 */
public enum PromotionEnum {

	/** 团购类型-全国拼团 */
	GROUP_BUY_TYPE_NATIONAL("全国拼团", 1),

	/** 包邮类型-卖家包邮 */
	FREE_TYPE_SELLER("卖家包邮", 1),
	/** 包邮类型-买家承担运费 */
	FREE_TYPE_BUYER("买家承担运费", 2),

	/** 优惠券抵扣-不支持 */
	COUPON_DEDUCTION_NO("支持", 0),
	/** 优惠券抵扣-支持 */
	COUPON_DEDUCTION_YES("不支持", 1),

	/** 审核状态-未审核 */
	AUDIT_STATE_UNAUDITED("未审核", 0),
	/** 审核状态-已审核 */
	AUDIT_STATE_AUDITED("已审核", 1),

	/** 发布状态-未发布 */
	PUBLISH_STATE_UNPUBLISHED("未发布", 0),
	/** 发布状态-已发布 */
	PUBLISH_STATE_PUBLISHED("已发布", 1),

	/** 会员类型-全部会员 */
	MEMBER_TYPE_ALL("全部会员", 1),
	/** 会员类型-指定会员 */
	MEMBER_TYPE_DESIGNATED("指定会员", 2),

	/** 终结状态-未终结 */
	FINISH_STATE_UNFINISHED("未终结", 0),
	/** 终结状态-已终结 */
	FINISH_STATE_END("已终结", 1),

	/** 团购状态-1待付款 */
	GROUP_BUY_STATE_WAIT_PAY("待付款", 1),
	/** 团购状态-2拼团中 */
	GROUP_BUY_STATE_IN_GROUP("拼团中", 2),
	/** 团购状态-3已成团 */
	GROUP_BUY_STATE_GROUP("已成团", 3),
	/** 团购状态-4已失效 */
	GROUP_BUY_STATE_INVALID("已失效", 4),
	/** 团购状态-5已退款 */
	GROUP_BUY_STATE_REFUND("已退款", 5),

	/** 团购类型-开团单 */
	GROUP_TYPE_OPEN("开团单", 1),
	/** 团购类型-参团单 */
	GROUP_TYPE_JOIN("参团单", 2),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private PromotionEnum(String value, Integer code) {
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
