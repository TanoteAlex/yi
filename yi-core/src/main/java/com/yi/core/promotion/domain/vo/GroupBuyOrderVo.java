/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yi.core.promotion.domain.simple.GroupBuyActivityProductSimple;
import com.yi.core.promotion.domain.simple.GroupBuyOrderSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 开团
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class GroupBuyOrderVo extends VoDomain implements Serializable {

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
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	/**
	 * 开团单
	 */
	private GroupBuyOrderSimple openGroupBuy;
	/**
	 * 参团单
	 */
	private List<GroupBuyOrderSimple> joinGroupBuys = new ArrayList<>(0);
	/**
	 * 团购商品（group_buy_activity_product表ID）
	 */
	@NotNull
	private GroupBuyActivityProductSimple groupBuyActivityProduct;
	/**
	 * 订单（订单表ID）
	 */
	@NotNull
	private SaleOrderSimple saleOrder;
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

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date endTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public GroupBuyActivityProductSimple getGroupBuyActivityProduct() {
		return groupBuyActivityProduct;
	}

	public void setGroupBuyActivityProduct(GroupBuyActivityProductSimple groupBuyActivityProduct) {
		this.groupBuyActivityProduct = groupBuyActivityProduct;
	}

	public SaleOrderSimple getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	@JsonIgnore
	public GroupBuyOrderSimple getOpenGroupBuy() {
		return openGroupBuy;
	}

	public void setOpenGroupBuy(GroupBuyOrderSimple openGroupBuy) {
		this.openGroupBuy = openGroupBuy;
	}

	public void setJoinGroupBuys(List<GroupBuyOrderSimple> joinGroupBuys) {
		this.joinGroupBuys = joinGroupBuys;
	}

	public List<GroupBuyOrderSimple> getJoinGroupBuys() {
		return joinGroupBuys;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

}