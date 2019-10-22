/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class CouponUseListVo extends ListVoDomain implements java.io.Serializable {

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
	@Max(9999999999L)
	private int couponId;
	/**
	 * 优惠码
	 */
	@Length(max = 16)
	private String couponNo;
	/**
	 * 优惠券领取（coupon_receive表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private int couponReceiveId;
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
	@Max(9999999999L)
	private int memberId;
	/**
	 * 会员账号
	 */
	@Length(max = 16)
	private String memberPhone;
	/**
	 * 使用时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date useTime;
	/**
	 * 订单（order表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private int orderId;
	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String orderNo;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;
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

	public int getCouponId() {
		return this.couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public int getCouponReceiveId() {
		return this.couponReceiveId;
	}

	public void setCouponReceiveId(int couponReceiveId) {
		this.couponReceiveId = couponReceiveId;
	}

	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getUse() {
		return this.use;
	}

	public void setUse(BigDecimal use) {
		this.use = use;
	}

	public BigDecimal getSurplus() {
		return this.surplus;
	}

	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Date getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}