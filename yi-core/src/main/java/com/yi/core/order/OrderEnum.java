package com.yi.core.order;

/**
 * 订单相关枚举
 * 
 * @author xuyh
 *
 */
public enum OrderEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 订单类型-0普通订单 */
	ORDER_TYPE_ORDINARY("普通订单", 0),
	/** 订单类型-1礼物订单 */
	ORDER_TYPE_GIFT("礼物订单", 1),
	/** 订单类型-2团购订单 */
	ORDER_TYPE_GROUP("团购订单", 2),
	/** 订单类型-3奖品订单 */
	ORDER_TYPE_PRIZE("奖品订单", 3),

	/** 订单状态-1待付款 */
	ORDER_STATE_WAIT_PAY("待付款", 1),
	/** 订单状态-2已付款待发货 */
	ORDER_STATE_WAIT_DELIVERY("待发货", 2),
	/** 订单状态-3已发货待收货 */
	ORDER_STATE_WAIT_RECEIPT("待收货", 3),
	/** 订单状态-4已收货已完成 */
	ORDER_STATE_COMPLETED("已完成", 4),
	/** 订单状态-5已关闭已取消 */
	ORDER_STATE_CLOSED("已关闭", 5),
	/** 订单状态-6已售后 */
	ORDER_STATE_AFTER_SALE("已售后", 6),

	/** 评价状态-1待评价 */
	COMMENT_STATE_WAIT("待评价", 1),
	/** 评价状态-2已评价 */
	COMMENT_STATE_ALREADY("已评价", 2),

	/** 售后状态-1可申请 */
	AFTER_SALE_STATE_CAN_APPLY("可申请", 1),
	/** 售后状态-2申请中 */
	AFTER_SALE_STATE_APPLY("申请中", 2),
	/** 售后状态-3已申请 */
	AFTER_SALE_STATE_COMPLETED("已申请", 3),
	/** 售后状态-4已过期 */
	AFTER_SALE_STATE_EXPIRE("已过期", 4),

	/** 对账状态-1待对账 */
	CHECK_STATE_WAIT("待对账", 1),
	/** 对账状态-2已对账 */
	CHECK_STATE_ALREADY("已对账", 2),

	/** 结算状态-1待结算 */
	SETTLEMENT_STATE_WAIT("待结算", 1),
	/** 结算状态-2已结算 */
	SETTLEMENT_STATE_ALREADY("已结算", 2),

	/** 礼物单类型-1送礼单 */
	GIFT_ORDER_TYPE_SEND("送礼单", 1),
	/** 礼物单类型-2收礼单 */
	GIFT_ORDER_TYPE_RECEIPT("收礼单", 2),

	/** 团购订单类型-1开团单 */
	GROUP_ORDER_TYPE_OPEN("开团单", 1),
	/** 团购订单类型-2参团单 */
	GROUP_ORDER_TYPE_JOIN("参团单", 2),

	/** 团购类型-1全国拼团 */
	GROUP_TYPE_NATIONAL("全国拼团", 1),
	/** 团购类型-2小区拼团 */
	GROUP_TYPE_COMMUNITY("小区拼团", 2),
	/** 团购类型-3返现拼团 */
	GROUP_TYPE_REBATE("返现拼团", 3),
	/** 团购类型-4秒杀活动 */
	GROUP_TYPE_SECKILL("秒杀活动", 4),

	/** 库存设置-1下单减库存 */
	STOCK_SET_ORDER("下单减库存", 1),
	/** 库存设置-2支付减库存 */
	STOCK_SET_PAYMENT("支付减库存", 2),

	// 运费模板相关
	/** 发货时间 12H内 */
	DELIVERY_TIME_12HOUR("12小时", 12),
	/** 发货时间 24H内 */
	DELIVERY_TIME_24HOUR("24小时", 24),
	/** 发货时间 1天内 */
	DELIVERY_TIME_1DAY("1天", 1),
	/** 发货时间 3天内 */
	DELIVERY_TIME_3DAY("3天", 3),
	/** 发货时间 5天内 */
	DELIVERY_TIME_5DAY("5天", 5),

	/** 运费类型-1自定义运费 */
	FREIGHT_TYPE_CUSTOM("自定义运费", 1),
	/** 运费类型-2卖家承担运费 */
	FREIGHT_TYPE_SELLER("卖家承担运费", 2),

	/** 计价方式-1按件数 */
	CHARGE_MODE_PIECE("按件数", 1),
	/** 计价方式-2按重量 */
	CHARGE_MODE_WEIGHT("按重量", 2),
	/** 计价方式-3按体积 */
	CHARGE_MODE_VOLUME("按体积", 3),

	/** 运送方式-1快递 */
	DELIVERY_MODE_EXPRESS("快递", 1),
	/** 运送方式-2EMS */
	DELIVERY_MODE_EMS("EMS", 2),
	/** 运送方式-3平邮 */
	DELIVERY_MODE("平邮", 3),

	/** 指定条件包邮-0未选中 */
	FREE_CONDITION_UNCHECKED("未选中", 0),
	/** 指定条件包邮-1选中 */
	FREE_CONDITION_CHECKED("选中", 1),

	/** 默认-1 */
	DEFAULT_YES("默认", 1),
	/** 非默认-0 */
	DEFAULT_NO("非默认", 0),

	/** 包邮条件-1件数 */
	FREE_TYPE_PIECE("件数", 1),
	/** 包邮条件-2金额 */
	FREE_TYPE_AMOUNT("金额", 2),
	/** 包邮条件-3件数+金额 */
	FREE_TYPE_PIECE_AND_AMOUNT("件数+金额", 3),

	/** 日志类型-1创建订单 */
	ORDER_LOG_STATE_CREATE_ORDER("创建订单", 1),
	/** 日志类型-2支付成功 */
	ORDER_LOG_STATE_PAY_SUCCESS("支付成功", 2),
	/** 日志类型-3开始配送 */
	ORDER_LOG_STATE_DISTRIBUTION("开始配送", 3),
	/** 日志类型-4确认收货 */
	ORDER_LOG_STATE_CONFIRM_RECEIPT("确认收货", 4),
	/** 日志类型-5关闭订单 */
	ORDER_LOG_STATE_CLOSE_ORDER("关闭订单", 5),
	/** 日志类型-6 订单退款 */
	ORDER_LOG_STATE_REFUND("订单退款", 6),

	/** 支付方式-1支付宝 */
	PAY_MODE_ALIPAY("支付宝", 1),
	/** 支付方式-2微信 */
	PAY_MODE_WECHAT_PAY("微信", 2),
	/** 支付方式-3银联 */
	PAY_MODE_UNIONPAY("银联", 3),
	/** 支付方式-3余额 */
	PAY_MODE_BALANCE("余额", 4),

//	/** 供应商阅读状态 -0未读 */
//	READ_TYPE_NO("未读", 0),
//	/** 供应商阅读状态 -1已读 */
//	READ_TYPE_YES("已读", 1),
	
	/** 供应商阅读状态 -0未读 */
	READ_STATE_UNREAD("未读", 0),
	/** 供应商阅读状态 -1已读 */
	READ_STATE_READ("已读", 1),

	/** 时间单位-1天 */
	TIME_UNIT_DAY("天", 1),
	/** 时间单位-2小时 */
	TIME_UNIT_HOUR("小时", 2),
	/** 时间单位-3分钟 */
	TIME_UNIT_MINUTE("分钟", 3),

	/** 订单设置类型 -1秒杀订单未付款 */
	ORDER_SET_TYPE_SECKILL("秒杀", 1),
	/** 订单设置类型 -2正常订单未付款 */
	ORDER_SET_TYPE_NORMAL("正常", 2),
	/** 订单设置类型 -3未收货 */
	ORDER_SET_TYPE_RECEIPT("收货", 3),
	/** 订单设置类型 -4交易 */
	ORDER_SET_TYPE_TRADE("交易", 4),
	/** 订单设置类型 -5未评论 */
	ORDER_SET_TYPE_COMMENT("评论", 5),

	/** 售后类型-1退款 */
	AFTER_SALE_TYPE_REFUND("退款", 1),
	/** 售后类型-2退货退款 */
	AFTER_SALE_TYPE_RETURN("退货退款", 2),
	/** 售后类型-3换货 */
	AFTER_SALE_TYPE_EXCHANGE("换货", 3),

	/** 申请状态-1审核中 */
	APPLY_STATE_AUDIT("审核中", 1),
	/** 申请状态-2处理中 */
	APPLY_STATE_PROCESS("处理中", 2),
	/** 申请状态-3已完成 */
	APPLY_STATE_FINISH("已完成", 3),

	/** 处理状态-1已申请待审核 */
	PROCESS_STATE_WAIT_AUDIT("待审核", 1),
	/** 处理状态-2确认退货待收货 */
	PROCESS_STATE_WAIT_RECEIPT("待收货", 2),
	/** 处理状态-3确认收货待退款 */
	PROCESS_STATE_WAIT_REFUND("待退款", 3),
	/** 处理状态-4已退款已完成 */
	PROCESS_STATE_FINISH("已完成", 4),
	/** 处理状态-5拒绝退货 */
	PROCESS_STATE_REFUSE_RETURN("拒绝退货", 5),
	/** 处理状态-6拒绝退款 */
	PROCESS_STATE_REFUSE_REFUND("拒绝退款", 6),

	/** 退款支付状态-1待回执 */
	REFUND_PAY_STATE_WAIT_RECEIPT("待回执", 1),
	/** 退款支付状态-2已回执 */
	REFUND_PAY_STATE_ALREADY_RECEIPT("已回执", 2),

	/** 退款方式（1退回原支付渠道） */
	REFUND_MODE_ORIGINAL_CHANNEL("原渠道", 1),

	/** 订单来源 -1公众号 */
	ORDER_SOURCE_SP("公众号", 1),
	/** 订单来源 -2小程序 */
	ORDER_SOURCE_MP("小程序", 2),
	/** 订单来源 -3APP */
	ORDER_SOURCE_APP("APP", 3),

	/** 支付渠道 -1公众号 */
	PAYMENT_CHANNEL_SP("公众号", 1),
	/** 支付渠道-2小程序 */
	PAYMENT_CHANNEL_MP("小程序", 2),
	/** 支付渠道 -3APP-微信 */
	PAYMENT_CHANNEL_APP_WECHAT("APP-微信", 3),
	/** 支付渠道 -4APP-支付宝 */
	PAYMENT_CHANNEL_APP_ALIPAY("APP-支付宝", 4),
	/** 支付渠道 -5余额 */
	PAYMENT_CHANNEL_BALANCE("余额", 5),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private OrderEnum(String value, Integer code) {
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
