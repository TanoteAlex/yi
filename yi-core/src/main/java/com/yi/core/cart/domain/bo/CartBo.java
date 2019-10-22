/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.domain.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 购物车
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class CartBo extends BoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 购物车商品ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private ProductBo product;
	/**
	 * 购买数量
	 */
	@NotNull
	@Max(9999999999L)
	private int quantity;
	/**
	 * 商品价格
	 */
	@Max(999999999999999L)
	private BigDecimal price;
	/**
	 * 优惠金额
	 */
	@Max(999999999999999L)
	private BigDecimal discount;
	/**
	 * 优惠信息
	 */
	@Length(max = 255)
	private String discountInfo;
	/**
	 * 状态（0有效1失效）
	 */
	private Integer state;
	// columns END

	/**
	 * 操作数量
	 */
	private int num;

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

	public MemberBo getMember() {
		return this.member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public ProductBo getProduct() {
		return this.product;
	}

	public void setProduct(ProductBo product) {
		this.product = product;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getDiscountInfo() {
		return this.discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}