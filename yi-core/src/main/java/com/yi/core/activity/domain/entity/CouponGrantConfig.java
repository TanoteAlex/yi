/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 优惠券发放配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class CouponGrantConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 发放配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 优惠券（coupon表ID）
	 */
	@NotNull
	private Coupon coupon;
	/**
	 * 发放策略（1一次性发放，2分批发放）
	 */
	@Max(127)
	private Integer grantStrategy;
	/**
	 * 一次性发放节点（1下单，2收货，3评论，4超过三包时效）
	 */
	@Max(127)
	private Integer grantNode;
	/**
	 * 冻结天数
	 */
	@Max(127)
	private Integer freezeDays;
	/**
	 * 解冻节点
	 */
	@Max(127)
	private Integer thawNode;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */

	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;
	// columns END

	private Set<CouponGrantRecord> couponGrantRecords = new HashSet<>(0);

	private List<CouponGrantStep> couponGrantSteps = new ArrayList<>(0);

	private Set<Commodity> commodities = new HashSet<>();

	public CouponGrantConfig() {
	}

	public CouponGrantConfig(int id) {
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_ID") })
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGrantStrategy() {
		return this.grantStrategy;
	}

	public void setGrantStrategy(Integer grantStrategy) {
		this.grantStrategy = grantStrategy;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGrantNode() {
		return this.grantNode;
	}

	public void setGrantNode(Integer grantNode) {
		this.grantNode = grantNode;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "couponGrantConfig")
	public Set<CouponGrantRecord> getCouponGrantRecords() {
		return couponGrantRecords;
	}

	public void setCouponGrantRecords(Set<CouponGrantRecord> couponGrantRecord) {
		this.couponGrantRecords = couponGrantRecord;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "couponGrantConfig")
	public List<CouponGrantStep> getCouponGrantSteps() {
		return couponGrantSteps;
	}

	public void setCouponGrantSteps(List<CouponGrantStep> couponGrantStep) {
		this.couponGrantSteps = couponGrantStep;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "couponGrantConfig")
	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getFreezeDays() {
		return freezeDays;
	}

	public void setFreezeDays(Integer freezeDays) {
		this.freezeDays = freezeDays;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getThawNode() {
		return thawNode;
	}

	public void setThawNode(Integer thawNode) {
		this.thawNode = thawNode;
	}

}