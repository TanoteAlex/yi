/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.domain.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 开团
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class GroupBuyOrderBo extends BoDomain implements Serializable {

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
	private MemberBo member;
	/**
	 * 开团单
	 */
	private GroupBuyOrderBo openGroupBuy;
	/**
	 * 参团单
	 */
	private List<GroupBuyOrderBo> joinGroupBuys = new ArrayList<>(0);
	/**
	 * 团购商品（group_buy_activity_product表ID）
	 */
	@NotNull
	private GroupBuyActivityProductBo groupBuyActivityProduct;
	/**
	 * 订单（订单表ID）
	 */
	@NotNull
	private SaleOrderBo saleOrder;
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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date endTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public GroupBuyActivityProductBo getGroupBuyActivityProduct() {
		return groupBuyActivityProduct;
	}

	public void setGroupBuyActivityProduct(GroupBuyActivityProductBo groupBuyActivityProduct) {
		this.groupBuyActivityProduct = groupBuyActivityProduct;
	}

	public SaleOrderBo getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderBo saleOrder) {
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

	public GroupBuyOrderBo getOpenGroupBuy() {
		return openGroupBuy;
	}

	public void setOpenGroupBuy(GroupBuyOrderBo openGroupBuy) {
		this.openGroupBuy = openGroupBuy;
	}

	public List<GroupBuyOrderBo> getJoinGroupBuys() {
		return joinGroupBuys;
	}

	public void setJoinGroupBuys(List<GroupBuyOrderBo> joinGroupBuys) {
		this.joinGroupBuys = joinGroupBuys;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

}