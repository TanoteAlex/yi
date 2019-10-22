/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 购物车
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CartVo extends VoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 购物车商品ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	private MemberSimple member;
	/**
	 * 商品（product表ID）
	 */
	private ProductSimple product;
	/**
	 * 购买数量
	 */
	private int quantity;
	/**
	 * 商品价格
	 */
	private BigDecimal price;
	/**
	 * 优惠金额
	 */
	private BigDecimal discount;
	/**
	 * 优惠信息
	 */
	private String discountInfo;
	/**
	 * 状态（0有效1失效）
	 */
	private Integer state;
	/**
	 * 添加时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
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

	public MemberSimple getMember() {
		return this.member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public ProductSimple getProduct() {
		return this.product;
	}

	public void setProduct(ProductSimple product) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}