/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.simple;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.order.domain.simple.SaleOrderSimple;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员佣金
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class MemberCommissionSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员佣金ID
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
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrderSimple saleOrder;
	/**
	 * 下级（member表ID）
	 */
	@NotNull
	private MemberSimple subordinate;
	/**
	 * 佣金等级（1一级佣金，2二级佣金，3小区佣金）
	 */
	@Max(127)
	private Integer commissionType;
	/**
	 * 结算状态（1未结算，2已结算，3已退回）
	 */
	@Max(127)
	private Integer settlementState;
	/**
	 * 佣金金额
	 */
	@Max(999999999999999L)
	private BigDecimal commissionAmount;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public SaleOrderSimple getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
	}

	public MemberSimple getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(MemberSimple subordinate) {
		this.subordinate = subordinate;
	}

	public Integer getCommissionType() {
		return this.commissionType;
	}

	public void setCommissionType(Integer commissionType) {
		this.commissionType = commissionType;
	}

	public Integer getSettlementState() {
		return this.settlementState;
	}

	public void setSettlementState(Integer settlementState) {
		this.settlementState = settlementState;
	}

	public BigDecimal getCommissionAmount() {
		return this.commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
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

}