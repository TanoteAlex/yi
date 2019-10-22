/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
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
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class CouponReceive implements java.io.Serializable {

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
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	private Integer myMemberId;
	/**
	 * 优惠券（coupon表ID）
	 */
	@NotNull
	private Coupon coupon;
	private Integer myCouponId;
	/**
	 * 订单（order表ID）
	 */
	private SaleOrder saleOrder;
	private  Integer mySaleOrderId;
	/**
	 * 订单编号
	 */
	@Length(max = 32)
	private String orderNo;
	/**
	 * 优惠券发放记录(coupon_grant_record表ID）
	 */
	private CouponGrantRecord couponGrantRecord;

	/**
	 * 优惠券类型（1满减券2代金券3储值券）
	 */
	private Integer couponType;
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
	 * 会员账号
	 */
	@Deprecated
	@Length(max = 16)
	private String memberPhone;
	/**
	 * 领取方式（1手工发放2自助领取）
	 */
	private Integer receiveMode;
	/**
	 * 领取时间
	 */
	private Date receiveTime;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 状态（1未使用2已使用3已失效4已收回5已充值）
	 */
	@NotNull
	private Integer state;
	/**
	 * 使用时间
	 */
	private Date useTime;
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
	private Date delTime;
	// columns END

	/**
	 * 优惠券使用集合
	 */
	private Set<CouponUse> couponUses;

	/**
	 * 优惠券使用选中状态 非数据库字段
	 */
	private boolean checked;

	public CouponReceive() {
	}

	public CouponReceive(int id) {
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

	@Column(unique = false, nullable = true, length = 64)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_ID", nullable = false) })
	@JsonIgnore
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ORDER_ID", nullable = true) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_GRANT_RECORD_ID", nullable = true) })
	public CouponGrantRecord getCouponGrantRecord() {
		return couponGrantRecord;
	}

	public void setCouponGrantRecord(CouponGrantRecord couponGrantRecord) {
		this.couponGrantRecord = couponGrantRecord;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getCouponType() {
		return this.couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getUsed() {
		return this.used;
	}

	public void setUsed(BigDecimal used) {
		this.used = used;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getSurplus() {
		return this.surplus;
	}

	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getReceiveMode() {
		return this.receiveMode;
	}

	public void setReceiveMode(Integer receiveMode) {
		this.receiveMode = receiveMode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	
	@Column(unique = false, nullable = true, length = 127)
	public String getFeelWord() {
		return feelWord;
	}

	public void setFeelWord(String feelWord) {
		this.feelWord = feelWord;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "couponReceive")
	public Set<CouponUse> getCouponUses() {
		return couponUses;
	}

	public void setCouponUses(Set<CouponUse> couponUse) {
		this.couponUses = couponUse;
	}

	@Transient
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Transient
	public Integer getMyMemberId() {
		return myMemberId;
	}

	public void setMyMemberId(Integer myMemberId) {
		this.myMemberId = myMemberId;
	}
	@Transient
	public Integer getMyCouponId() {
		return myCouponId;
	}

	public void setMyCouponId(Integer myCouponId) {
		this.myCouponId = myCouponId;
	}
	@Transient
	public Integer getMySaleOrderId() {
		return mySaleOrderId;
	}

	public void setMySaleOrderId(Integer mySaleOrderId) {
		this.mySaleOrderId = mySaleOrderId;
	}
}