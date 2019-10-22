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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yihz.common.json.deserializer.JsonDateDeserializer;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonDateSerializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 优惠券
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
public class Coupon implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 用户ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 优惠券编码
	 */
	@Length(max = 16)
	private String couponNo;
	/**
	 * 优惠券名称
	 */
	@Length(max = 32)
	private String couponName;
	/**
	 * 优惠券类型（1满减券2代金券3储值券）
	 */
	private Integer couponType;
	/**
	 * 面值
	 */
	@NotNull
	@Max(999999999999999L)
	private BigDecimal parValue;
	/**
	 * 发放数量
	 */
	@NotNull
	@Max(9999999999L)
	private int quantity;
	/**
	 * 领取数量
	 */
	@Max(9999999999L)
	private int receiveQuantity;
	/**
	 * 使用数量
	 */
	@Max(9999999999L)
	private int useQuantity;

	/**
	 * 使用条件类型（0无限制，1满XX元可用，2满XX件可用）
	 */
	private Integer useConditionType;
	/**
	 * 满XX元可用
	 */
	private BigDecimal fullMoney;
	/**
	 * 满XX件可用
	 */
	private int fullQuantity;
	/**
	 * 领取方式（1手工发放，2自助领取，3自动发放）
	 */
	private Integer receiveMode;
	/**
	 * 每人限领（不限制为空）
	 */
	private Integer limited;
	/**
	 * 有效期类型（1时间段2固定天数）
	 */
	private Integer validType;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 固定天数（领取后到期天数）
	 */
	@Max(9999999999L)
	private Integer fixedDay;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 情怀文案
	 */
	private String feelWord;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END
	/**
	 * 商品集合
	 */
	private Set<Commodity> commodities;
	/**
	 * 会员等级集合
	 */
	private Set<MemberLevel> memberLevels;

	public Coupon() {
	}

	public Coupon(int id) {
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

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 16)
	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getCouponType() {
		return this.couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getUseQuantity() {
		return this.useQuantity;
	}

	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getReceiveMode() {
		return this.receiveMode;
	}

	public void setReceiveMode(Integer receiveMode) {
		this.receiveMode = receiveMode;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getLimited() {
		return this.limited;
	}

	public void setLimited(Integer limited) {
		this.limited = limited;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getValidType() {
		return this.validType;
	}

	public void setValidType(Integer validType) {
		this.validType = validType;
	}

	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getFixedDay() {
		return this.fixedDay;
	}

	public void setFixedDay(Integer fixedDay) {
		this.fixedDay = fixedDay;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "coupon_commodity", joinColumns = @JoinColumn(name = "COUPON_ID"), inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
	@Where(clause = "deleted=0")
	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "coupon_member_level", joinColumns = @JoinColumn(name = "COUPON_ID"), inverseJoinColumns = @JoinColumn(name = "MEMBER_LEVEL_ID"))
	@Where(clause = "deleted=0")
	public Set<MemberLevel> getMemberLevels() {
		return memberLevels;
	}

	public void setMemberLevels(Set<MemberLevel> memberLevels) {
		this.memberLevels = memberLevels;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(int receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getUseConditionType() {
		return useConditionType;
	}

	public void setUseConditionType(Integer useConditionType) {
		this.useConditionType = useConditionType;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getFullMoney() {
		return fullMoney;
	}

	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getFullQuantity() {
		return fullQuantity;
	}

	public void setFullQuantity(int fullQuantity) {
		this.fullQuantity = fullQuantity;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getFeelWord() {
		return feelWord;
	}

	public void setFeelWord(String feelWord) {
		this.feelWord = feelWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 0)
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

}