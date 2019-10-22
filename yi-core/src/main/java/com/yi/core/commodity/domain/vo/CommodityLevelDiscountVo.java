/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yihz.common.convert.domain.VoDomain;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CommodityLevelDiscountVo extends VoDomain implements java.io.Serializable {

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
	 * 会员级（member_level表ID）
	 */
	@NotNull
	private MemberLevelSimple memberLevel;
	/**
	 * 折扣（0.00-100.00）
	 */
	@NotNull
	private BigDecimal discount;
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

}