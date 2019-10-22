/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.cart.CartEnum;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 购物车
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Cart implements java.io.Serializable {

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
	 * 会员
	 */
	private Member member;
	/**
	 * 货品
	 */
	private Product product;
	/**
	 * 购买数量
	 */
	@NotNull
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
	@Length(max = 255)
	private String discountInfo;
	// columns END
	/**
	 * 状态（0有效1失效）
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Cart() {
	}

	public Cart(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(nullable = false, length = 10)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(length = 15)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(length = 15)
	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(length = 255)
	public String getDiscountInfo() {
		return this.discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = true) })
	public Product getProduct() {
		return product;
	}

	@Column(unique = false, nullable = false,  length = 0)
	public Integer getState() {
		if (product == null || Deleted.DEL_TRUE.equals(product.getDeleted())) {
			state = CartEnum.STATE_INVALID.getCode();
		}
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true,  length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}