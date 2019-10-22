/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 团购单
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class GroupBuyOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 开团表ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 开团单
	 */
	private GroupBuyOrder openGroupBuy;
	/**
	 * 参团单
	 */
	private List<GroupBuyOrder> joinGroupBuys = new ArrayList<>(0);
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	/**
	 * 团购商品（group_buy_activity_product表ID）
	 */
	@NotNull
	private GroupBuyActivityProduct groupBuyActivityProduct;
	/**
	 * 订单（订单表ID）
	 */
	@NotNull
	private SaleOrder saleOrder;

	/**
	 * 团购状态（1待付款，2拼团中，3已成团，4已失效）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 团购类型（1开团单，2参团单）
	 */
	@Max(127)
	private Integer groupType;
	/**
	 * 成团人数
	 */
	@Max(127)
	private Integer groupNum;
	/**
	 * 参团人数
	 */
	@Max(127)
	private Integer joinNum;
	/**
	 * 开始时间
	 */

	private Date startTime;
	/**
	 * 结束时间
	 */

	private Date endTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
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

	public GroupBuyOrder() {
	}

	public GroupBuyOrder(int id) {
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

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
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

	@Column(unique = false, nullable = true, length = 127)
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

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALE_ORDER_ID", nullable = true) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GROUP_BUY_ACTIVITY_PRODUCT_ID", nullable = false) })
	public GroupBuyActivityProduct getGroupBuyActivityProduct() {
		return groupBuyActivityProduct;
	}

	public void setGroupBuyActivityProduct(GroupBuyActivityProduct groupBuyActivityProduct) {
		this.groupBuyActivityProduct = groupBuyActivityProduct;
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
	@JoinColumns({ @JoinColumn(name = "PARENT_ID", nullable = false) })
	public GroupBuyOrder getOpenGroupBuy() {
		return openGroupBuy;
	}

	public void setOpenGroupBuy(GroupBuyOrder openGroupBuy) {
		this.openGroupBuy = openGroupBuy;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "openGroupBuy")
	public List<GroupBuyOrder> getJoinGroupBuys() {
		return joinGroupBuys;
	}

	public void setJoinGroupBuys(List<GroupBuyOrder> joinGroupBuy) {
		this.joinGroupBuys = joinGroupBuy;
	}

}