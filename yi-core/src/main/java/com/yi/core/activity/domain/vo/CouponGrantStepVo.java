/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yi.core.activity.domain.simple.CouponGrantConfigSimple;
import com.yihz.common.convert.domain.VoDomain;

/**
 * 发放步骤
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class CouponGrantStepVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 发放步骤ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 优惠券发放配置（coupon_grant_config表ID）
	 */
	@NotNull
	private CouponGrantConfigSimple couponGrantConfig;
	/**
	 * 发放节点（1购买，2收货，3评论，4超过三包时效）
	 */
	@Max(127)
	private Integer grantNode;
	/**
	 * 发放比例（0.00-100.00）
	 */
	@NotNull
	@Max(99999L)
	private BigDecimal grantRate;

	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public CouponGrantConfigSimple getCouponGrantConfig() {
		return this.couponGrantConfig;
	}

	public void setCouponGrantConfig(CouponGrantConfigSimple couponGrantConfig) {
		this.couponGrantConfig = couponGrantConfig;
	}

	public Integer getGrantNode() {
		return this.grantNode;
	}

	public void setGrantNode(Integer grantNode) {
		this.grantNode = grantNode;
	}

	public BigDecimal getGrantRate() {
		return grantRate;
	}

	public void setGrantRate(BigDecimal grantRate) {
		this.grantRate = grantRate;
	}
}