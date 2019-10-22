/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
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
public class InvitePrize implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 邀请奖品ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 邀请有礼活动（invite_activity表id）
	 */
	@NotNull
	private InviteActivity inviteActivity;
	/**
	 * 奖品编码
	 */
	@Length(max = 16)
	private String prizeNo;
	/**
	 * 奖品名称
	 */
	@Length(max = 32)
	private String prizeName;
	/**
	 * 邀请人数
	 */
	@Max(9999999999L)
	private Integer inviteNum;
	/**
	 * 奖品类型（1积分，2商品，3优惠券）
	 */
	@Max(127)
	private Integer prizeType;
	/**
	 * 积分
	 */
	@Max(9999999999L)
	private Integer integral;
	/**
	 * 商品（commodity表ID）
	 */
	private Commodity commodity;
	/**
	 * 商品（product表ID）
	 */
	private Product product;
	/**
	 * 优惠券（coupon表ID）
	 */
	private Coupon coupon;
	/**
	 * 排序
	 */

	private Integer sort;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 创建时间
	 */

	private Date createTime;
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

	public InvitePrize() {
	}

	public InvitePrize(java.lang.Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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
	@JoinColumns({ @JoinColumn(name = "INVITE_ACTIVITY_ID", nullable = false, updatable = false) })
	public InviteActivity getInviteActivity() {
		return inviteActivity;
	}

	public void setInviteActivity(InviteActivity inviteActivity) {
		this.inviteActivity = inviteActivity;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getPrizeNo() {
		return this.prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getPrizeName() {
		return this.prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getInviteNum() {
		return this.inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getPrizeType() {
		return this.prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false, updatable = true) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = true) })
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_ID", nullable = false, updatable = true) })
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = false, length = 0)
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
	public Date getCreateTime() {
		return this.createTime;
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