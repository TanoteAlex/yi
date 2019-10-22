/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 储值券使用记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class CouponUse implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 64)
	private String guid;
	/**
	 * 优惠券（coupon表ID）
	 */
	@NotNull
	private Coupon coupon;
	/**
	 * 优惠码
	 */
	@Length(max = 16)
	private String couponNo;
	/**
	 * 优惠券领取（coupon_receive表ID）
	 */
	@NotNull
	private CouponReceive couponReceive;
	/**
	 * 面值
	 */
	@Max(999999999999999L)
	private BigDecimal parValue;
	/**
	 * 使用
	 */
	@Max(999999999999999L)
	private BigDecimal use;
	/**
	 * 剩余
	 */
	@Max(999999999999999L)
	private BigDecimal surplus;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	/**
	 * 会员账号
	 */
	@Length(max = 16)
	private String memberPhone;
	/**
	 * 使用时间
	 */

	private Date useTime;
	/**
	 * 订单（order表ID）
	 */
	@NotNull
	private SaleOrder saleOrder;
	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String orderNo;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;
	// columns END

	public CouponUse() {
	}

	public CouponUse(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_ID", nullable = false) })
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_RECEIVE_ID", nullable = false) })
	public CouponReceive getCouponReceive() {
		return couponReceive;
	}

	public void setCouponReceive(CouponReceive couponReceive) {
		this.couponReceive = couponReceive;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getUse() {
		return this.use;
	}

	public void setUse(BigDecimal use) {
		this.use = use;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getSurplus() {
		return this.surplus;
	}

	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	@JsonIgnore
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ORDER_ID", nullable = false) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}