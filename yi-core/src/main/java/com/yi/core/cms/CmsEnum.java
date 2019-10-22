package com.yi.core.cms;

/**
 * 内容管理 相关枚举
 * 
 * @author xuyh
 *
 */
public enum CmsEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 专题文章 */
	ARTICLE_TYPE_SPECIAL("专题文章", 1),
	/** 主题文章 */
	ARTICLE_TYPE_THEME("主题文章", 2),

	/** 推荐位展示方式 展示2个 */
	SHOW_MODE_2("展示2个", 2),
	/** 推荐位展示方式 展示3个 */
	SHOW_MODE_3("展示3个", 3),
	/** 推荐位展示方式 展示4个 */
	SHOW_MODE_4("展示4个", 4),
	/** 推荐位展示方式 展示5个 */
	SHOW_MODE_5("展示5个", 5),

//	/** 今日推荐 */
//	RECOMMEND_TYPE_TODAY("今日推荐", 1),
//	/** 楼层推荐 */
//	RECOMMEND_TYPE_FLOOR("楼层推荐", 2),

	/** 首页轮播图 */
	POSITION_TYPE_BANNER("首页轮播图", 1),
	/** 楼层推荐位 */
	POSITION_TYPE_FLOOR("楼层推荐位", 2),
	/** 今日推荐位 */
	POSITION_TYPE_TODAY("今日推荐位", 3),
	/** 购物车推荐位 */
	POSITION_TYPE_CART("购物车推荐位", 4),
	/** 邀请有礼推荐位 */
	POSITION_TYPE_INVITE("邀请有礼推荐位", 5),
	/** 优惠券推荐位 */
	POSITION_TYPE_COUPON("优惠券推荐位", 6),

	/** 广告链接类型-商品 */
	LINK_TYPE_COMMODITY("商品", 1),
	/** 广告链接类型-文件 */
	LINK_TYPE_ARTICLE("文章", 2),

	/** 显示 */
	DISPLAY_YES("显示", 0),
	/** 不显示 */
	DISPLAY_NO("不显示", 1),

	;
	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private CmsEnum(String value, Integer code) {
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
