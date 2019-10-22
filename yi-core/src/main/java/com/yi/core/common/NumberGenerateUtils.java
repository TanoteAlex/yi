package com.yi.core.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yi.core.utils.PinyinUtils;
import com.yihz.common.utils.RandomUtils;

/**
 * 编号生成工具类
 * 
 * @author xuyh
 *
 */
public class NumberGenerateUtils {

	public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmssSSS");

	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddSSS");

	public final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyMMddHHmmss");

	public final static SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("yyMMddHHmmssSSS");

	public final static String ORDER_PREFIX = "o";

	/**
	 * 生成 属性组编码
	 * 
	 * @return
	 */
	public static String generateAttributeGroupNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 商品分类编码
	 * 
	 * @return
	 */
	public static String generateCategoryNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 商品编码
	 *
	 * @return
	 */
	public static String generateCommodityNo() {
		return generateNo(null, null);
	}

	/**
	 * 快递单模板编号
	 */
	public static String generateExpressTemplateNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 货品编码
	 *
	 * @return
	 */
	public static String generateProductNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 运营分类编码
	 * 
	 * @return
	 */
	public static String generateOperateCategoryNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 运费模板编码
	 * 
	 * @return
	 */
	public static String generateFreightTemplateNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 拼团编码
	 * 
	 * @return
	 */
	public static String generateGroupNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成订单编号
	 * 
	 * @return
	 */
	public static String generateOrderNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成优惠券编号
	 * 
	 * @return
	 */
	public static String generateCouponNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成售后单号
	 * 
	 * @return
	 */
	public static String generateAfterSaleOrderNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成退货单号
	 * 
	 * @return
	 */
	public static String generateReturnOrderNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成退款单号
	 * 
	 * @return
	 */
	public static String generateRefundOrderNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成评论编号
	 */
	public static String generateCommentNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成 供应商编码
	 *
	 * @return
	 */
	public static String generateSupplierNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成角色编号
	 */
	public static String generateRoleNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成部门编号
	 */
	public static String generateDeptNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成资源编号
	 */
	public static String generateRescNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成礼包编号
	 */
	public static String generateGiftBagNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成礼物编号
	 */
	public static String generateGiftNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成奖励编号
	 */
	public static String generateRewardNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成奖品编号
	 */
	public static String generatePrizeNo() {
		return generateNo(null, null);
	}

	/**
	 * 生成商品编码
	 * 
	 * @return
	 */
	public static String generateCommodityNo(String supplierName) {
		if (StringUtils.isNotBlank(supplierName)) {
			String prefix = PinyinUtils.getPinYinHeaderChar(supplierName);
			if (prefix.length() > 4) {
				prefix = prefix.substring(0, 4);
			}
			return prefix + DATE_TIME_FORMAT.format(new Date());
		}
		return DATE_TIME_FORMAT.format(new Date()) + RandomUtils.randomString(4, RandomUtils.RANDRULE.RAND_NUMBER);
	}

	/**
	 * 生成序列号
	 * 
	 * @return
	 */
	public static String generateSerialNo() {
		return FULL_DATE_TIME_FORMAT.format(new Date());
	}

	/**
	 * 生成编码
	 * 
	 * @param prefix
	 * @param Suffix
	 */
	public synchronized static String generateNo(String prefix, String suffix) {
		if (prefix == null) {
			prefix = "";
		}
		if (suffix == null) {
			suffix = "";
		}
		String no = prefix + DATE_TIME_FORMAT.format(new Date()) + RandomUtils.randomString(4, RandomUtils.RANDRULE.RAND_NUMBER) + suffix;
		return no;
	}

	/**
	 * 生成 仓库编码
	 *
	 * @return
	 */
	public static String generateWarehouse() {
		return generateNo(null, null);
	}

}
