package com.yi.core.commodity;

/**
 * 商品相关枚举
 * 
 * @author xuyh
 *
 */
public enum CommodityEnum {

	/** 启用 */
	STATE_ENABLE("启用", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 统一运费 */
	FREIGHT_SET_UNIFIED("统一运费", 1),
	/** 运费模板 */
	FREIGHT_SET_TEMPLATE("运费模板", 2),

	/** 下单减库存 */
	STOCK_SET_ORDER("下单减库存", 1),
	/** 支付减库存 */
	STOCK_SET_PAY("支付减库存", 2),

	/** 上架状态-立即上架 */
	SHELF_STATE_ON("立即上架", 1),
	/** 上架状态-放入仓库 */
	SHELF_STATE_WAREHOUSE("放入仓库", 2),

	/** 审核状态-待审核 */
	AUDIT_STATE_WAIT("待审核", 1),
	/** 审核状态-审核通过 */
	AUDIT_STATE_PASS("审核通过", 2),
	/** 审核状态-审核拒绝 */
	AUDIT_STATE_REJECT("审核拒绝", 3),

	/** 商品类型-普通商品 */
	COMMODITY_TYPE_ORDINARY("普通商品", 0),
	/** 商品类型-批发商品 */
	COMMODITY_TYPE_WHOLESALE("批发商品", 1),
	/** 商品类型-送礼商品 */
	COMMODITY_TYPE_GIFT("送礼商品", 2),
	/** 商品类型-限量商品 */
	COMMODITY_TYPE_LIMIT("限量商品", 3),

	/** 买送券 */
	COUPON_TYPE_GIVE("买送券", 2),
	/** 评论积分 */
	COMMENT_INTEGRAL("评论送积分", 3),

	/** 显示 */
	DISPLAY_YES("显示", 0),
	/** 不显示 */
	DISPLAY_NO("不显示", 1),

	/** 评论星级-一星 */
	COMMENT_STAR_ONE("一星", 1),
	/** 评论星级-二星 */
	COMMENT_STAR_TWO("二星", 2),
	/** 评论星级-三星 */
	COMMENT_STAR_THREE("三星", 3),
	/** 评论星级-四星 */
	COMMENT_STAR_FOUR("四星", 4),
	/** 评论星级-五星 */
	COMMENT_STAR_FIVE("五星", 5),

	/** 是否参与分销-0参加 */
	DISTRIBUTION_YES("参加", 0),
	/** 是否参与分销-1不参加 */
	COMMENT_STAR_NO("不参加", 1),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private CommodityEnum(String value, Integer code) {
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
