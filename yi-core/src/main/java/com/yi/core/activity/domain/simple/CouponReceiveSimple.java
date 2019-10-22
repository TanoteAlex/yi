/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.simple;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 优惠券领取
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */

public class CouponReceiveSimple extends SimpleDomain implements java.io.Serializable {

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
	private CouponSimple coupon;
	private Integer myCouponId;
	/**
	 * 优惠码
	 */
	@Length(max = 16)
	private String couponNo;
	/**
	 * 面值
	 */
	@Max(999999999999999L)
	private BigDecimal parValue;
	/**
	 * 使用
	 */
	@Max(999999999999999L)
	private BigDecimal used;
	/**
	 * 剩余
	 */
	@Max(999999999999999L)
	private BigDecimal surplus;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	private Integer myMemberId;
	/**
	 * 会员账号
	 */
	@NotBlank
	@Length(max = 16)
	private String memberPhone;
	/**
	 * 领取方式（1手工发放2自助领取）
	 */

	private Integer receiveMode;
	/**
	 * 领取时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date receiveTime;
	/**
	 * 开始时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date endTime;
	/**
	 * 状态（1未使用2已使用3已失效4已收回）
	 */
	@NotNull
	private Integer state;
	/**
	 * 使用时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date useTime;
	/**
	 * 订单（order表ID）
	 */
	private SaleOrderSimple saleOrder;
	private Integer mySaleOrderId;

	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String orderNo;
	/**
	 * 情怀语
	 */
	private String feelWord;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 优惠券使用选中状态 非数据库字段
	 */
	private boolean checked;

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

	public CouponSimple getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponSimple coupon) {
		this.coupon = coupon;
	}

	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getUsed() {
		return this.used;
	}

	public void setUsed(BigDecimal used) {
		this.used = used;
	}

	public BigDecimal getSurplus() {
		return this.surplus;
	}

	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	public MemberSimple getMember() {
		return this.member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Integer getReceiveMode() {
		return this.receiveMode;
	}

	public void setReceiveMode(Integer receiveMode) {
		this.receiveMode = receiveMode;
	}

	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	@JsonIgnore
	public SaleOrderSimple getSaleOrder() {
		return this.saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getFeelWord() {
		return feelWord;
	}

	public void setFeelWord(String feelWord) {
		this.feelWord = feelWord;
	}

	@Transient
	public Integer getMyCouponId() {
		return myCouponId;
	}

	public void setMyCouponId(Integer myCouponId) {
		this.myCouponId = myCouponId;
	}
	@Transient
	public Integer getMyMemberId() {
		return myMemberId;
	}

	public void setMyMemberId(Integer myMemberId) {
		this.myMemberId = myMemberId;
	}
	@Transient
	public Integer getMySaleOrderId() {
		return mySaleOrderId;
	}

	public void setMySaleOrderId(Integer mySaleOrderId) {
		this.mySaleOrderId = mySaleOrderId;
	}


}