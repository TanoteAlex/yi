package com.yi.core.member;

/**
 * 会员相关枚举
 * 
 * @author xuyh
 *
 */
public enum MemberEnum {

	/** 未知 */
	SEX_UNKNOWN("未知", 0),
	/** 男 */
	SEX_MALE("男", 1),
	/** 女 */
	SEX_FEMALE("女", 2),

	/** 普通会员 */
	MEMBER_TYPE_ORDINARY("普通会员", 0),
	/** 管理员 */
	MEMBER_TYPE_ADMIN("管理员", 1),

	/** 启用 */
	STATE_ENABLE("启用", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 非vip会员 */
	VIP_NO("非vip会员", 0),
	/** vip会员 */
	VIP_YES("vip会员", 1),

	/** 默认地址 */
	ADDRESS_DEFAULT("默认地址", 1),
	/** 非默认地址 */
	ADDRESS_NON_DEFAULT("非默认地址", 0),

	/** 非默认 */
	DEFAULT_NO("非默认", 0),
	/** 默认 */
	DEFAULT_YES("默认", 1),
	

	/** 佣金 */
	TRADE_TYPE_COMMISSION("佣金转入", 1),
	/** 在线支付 */
	TRADE_TYPE_ONLINE_PAYMENT("在线支付", 2),
	/** 提现 */
	TRADE_TYPE_WITHDRAW_CASH("提现", 3),
	/** 小区提成 */
	TRADE_TYPE_COMMUNITY_COMMISSION("小区提成", 4),
	/** 送礼支付 */
	TRADE_TYPE_GIFT_PAYMENT("送礼支付", 5),
	/** 退款 */
	TRADE_TYPE_REFUND("退款", 6),
	/** 退款扣佣金 */
	TRADE_TYPE_RETURN_COMMISSION("扣佣金", 7),
	/** 平台充值-储值券发放 */
	TRADE_TYPE_RECHARGE("平台充值", 8),
	/** 余额支付 */
	TRADE_TYPE_BALANCE_PAYMENT("余额支付", 9),
	/** 退余额 */
	TRADE_TYPE_RETURN_BALANCE("退余额", 10),
	/** 平台扣减-储值券撤回扣减 */
	TRADE_TYPE_SUBTRACT("平台扣减", 11),
	/** 积分抵现 */
	TRADE_TYPE_INTEGRAL_CASH("积分抵现", 12),

	/** 收入 */
	OPERATE_TYPE_INCOME("收入", 1),
	/** 支出 */
	OPERATE_TYPE_EXPENDITURE("支出", 2),

	/** 分销等级-一级 */
	DISTRIBUTION_LEVEL_FIRST("一级", 1),
	/** 分销等级-二级 */
	DISTRIBUTION_LEVEL_SECOND("二级", 2),

	/** 佣金类型-一级佣金 */
	COMMISSION_TYPE_FIRST("一级", 1),
	/** 佣金类型-二级佣金 */
	COMMISSION_TYPE_SECOND("二级", 2),
	/** 佣金类型-小区佣金 */
	COMMISSION_TYPE_COMMUNITY("小区", 3),

	/** 结算状态-1未结算 */
	SETTLEMENT_STATE_UNSETTLED("未结算", 1),
	/** 结算状态-2已结算 */
	SETTLEMENT_STATE_SETTLED("已结算", 2),
	/** 结算状态-3已退回 */
	SETTLEMENT_STATE_RETURN("已退回", 3),

	;

	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private MemberEnum(String value, Integer code) {
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
