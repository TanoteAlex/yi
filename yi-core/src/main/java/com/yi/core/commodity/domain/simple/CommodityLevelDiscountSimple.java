/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.simple;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yihz.common.convert.domain.SimpleDomain;

/**
 * 会员等级折扣
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CommodityLevelDiscountSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 商品等级折扣ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 商品（commodity表ID）
	 */
	@NotNull
	private CommoditySimple commodity;
	/**
	 * 会员等级（member_level表ID）
	 */
	@NotNull
	private MemberLevelSimple memberLevel;
	/**
	 * 折扣（0.00-100.00）
	 */
	@NotNull
	private BigDecimal discount;

	/**
	 * 折扣价
	 */
	private BigDecimal discountPrice;
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

	// @JsonIgnore
	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public MemberLevelSimple getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(MemberLevelSimple memberLevel) {
		this.memberLevel = memberLevel;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

}